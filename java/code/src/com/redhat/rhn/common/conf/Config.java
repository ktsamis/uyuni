/*
 * Copyright (c) 2024 SUSE LLC
 * Copyright (c) 2009--2014 Red Hat, Inc.
 *
 * This software is licensed to you under the GNU General Public License,
 * version 2 (GPLv2). There is NO WARRANTY for this software, express or
 * implied, including the implied warranties of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. You should have received a copy of GPLv2
 * along with this software; if not, see
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt.
 *
 * Red Hat trademarks are not licensed under GPLv2. No permission is
 * granted to use or replicate Red Hat trademarks that are incorporated
 * in this software or its documentation.
 */

package com.redhat.rhn.common.conf;

import com.redhat.rhn.common.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * The Config class acts as an abstraction layer between our configuration
 * system, and the actual implementation. The basic idea is that there is a
 * global config, but you can instantiate one of your own if you want. This
 * layer insulates us from changing the underlying implementation.
 * <p>
 * Config files are properties, with /usr/share/rhn/config-defaults/rhn.conf
 * setting defaults that can be overridden by /etc/rhn/rhn.conf.
 *
 */
public class Config {

    private static final Logger LOGGER = LogManager.getLogger(Config.class);

    //
    // Location of config files
    //

    /**
     * The default directory in which to look for config files
     */
    private static final String DEFAULT_CONF_DIR = "/etc/rhn";

    /**
     * The directory in which to look for default config values
     */
    private static final String DEFAULT_DEFAULT_CONF_DIR = "/usr/share/rhn/config-defaults";

    /**
     * The system property containing the configuration directory.
     * If the property is not set, config files are read
     * from {@link #DEFAULT_CONF_DIR}
     */
    private static final String CONF_DIR_PROPERTY = "rhn.config.dir";

    /**
     * List of values that are considered true, ignoring case.
     */
    private static final String[] TRUE_VALUES = {"1", "y", "true", "yes", "on"};

    /**
     * array of prefix in the order they should be search
     * if the given lookup string is without a namespace.
     */
    private final String[] prefixOrder = new String[] {"web", "server"};
    private static Config singletonConfig = null;
    /** hash of configuration properties */
    private final Properties configValues = new Properties();
    /** set of configuration file names */
    private final TreeSet<Path> fileList = new TreeSet<>((f1, f2) -> {
        // Need to make sure we read the child namespace before the base
        // namespace.  To do that, we sort the list in reverse order based
        // on the length of the file name.  If two filenames have the same
        // length, then we need to do a lexigraphical comparison to make
        // sure that the filenames themselves are different.
        int lenDif = f2.toAbsolutePath().toString().length() - f1.toAbsolutePath().toString().length();
        if (lenDif != 0) {
            return lenDif;
        }

        return f2.compareTo(f1);
    });

    /**
     * public constructor. Rereads config entries every time it is called.
     *
     * @throws ConfigException error from the Configuration layers. the jakarta
     * commons conf system just throws Exception, which makes it hard to react.
     * sometioes it is an IOExceptions, sometimes a SAXParserException,
     * sometimes a VindictiveException. so we just turn them into our own
     * exception type and toss them up. as we discover ones we might
     * meaningfully want to react to, we can specilize ConfigException and catch
     * those
     */
    public Config() throws ConfigException {
        addPath(DEFAULT_DEFAULT_CONF_DIR);
        addPath(getDefaultConfigFilePath());
        parseFiles();
    }

    /**
     * Read the entries only for the specified path list.
     *
     * @param pathList the list of path to be evaluated
     */
    public Config(Collection<String> pathList) {
        pathList.forEach(this::addPath);
        parseFiles();
    }

    /**
     * Add a path to the config object for parsing
     * @param path The path to add
     */
    public void addPath(String path) {
        getFiles(path);
    }

    /**
     * static method to get the singleton Config option
     *
     * @return the config option
     */
    public static synchronized Config get() {
        if (singletonConfig == null) {
            singletonConfig = new Config();
        }
        return singletonConfig;
    }

    /**
     * Clears the singleton configuration
     */
    public static synchronized void clear() {
        singletonConfig = null;
    }

    private static String getDefaultConfigDir() {
        String confDir = System.getProperty(CONF_DIR_PROPERTY);

        if (StringUtils.isBlank(confDir)) {
            confDir = DEFAULT_CONF_DIR;
        }
        return confDir;
    }

    /**
     * Get the path to the rhn.conf file we use.
     *
     * @return String path.
     */
    public static String getDefaultConfigFilePath() {
        return getDefaultConfigDir() + "/rhn.conf";
    }

    /**
     * Get the configuration entry for the given string name.  If the value
     * is null, then return the given defValue.  defValue can be null as well.
     * @param name name of property
     * @param defValue default value for property if it is null.
     * @return the value of the property with the given name, or defValue.
     */
    public String getString(String name, String defValue) {
        String ret = getString(name);
        if (ret == null) {
            LOGGER.debug("getString() - returning default value");
            return defValue;
        }
        return ret;
    }

    /**
     * @param name Key to check for
     * @return true if the config contains key
     */
    public boolean containsKey(String name) {
        return configValues.containsKey(name);
    }

    /**
     * get the config entry for string s
     *
     * @param value string to get the value of
     * @return the value
     */
    public String getString(String value) {
        LOGGER.debug("getString() -     getString() called with: {}", () -> StringUtil.sanitizeLogInput(value));
        if (value == null) {
            return null;
        }

        int lastDot = value.lastIndexOf('.');
        String ns = "";
        String property = value;
        if (lastDot > 0) {
            property = value.substring(lastDot + 1);
            ns = value.substring(0, lastDot);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getString() -     getString() -> Getting property: {}",
                    StringUtil.sanitizeLogInput(property));
        }
        String result = configValues.getProperty(property);
        LOGGER.debug("getString() -     getString() -> result: {}", result);
        if (result == null) {
            if (!ns.isEmpty()) {
                result = configValues.getProperty(ns + "." + property);
            }
            else {
                for (String prefix : prefixOrder) {
                    result = configValues.getProperty(prefix + "." + property);
                    if (result != null) {
                        break;
                    }
                }
            }
        }
        LOGGER.debug("getString() -     getString() -> returning: {}", result);

        if (StringUtils.isEmpty(result)) {
            return null;
        }

        return StringUtils.trim(result);
    }

    /**
     * get the config entry for string s
     *
     * @param s string to get the value of
     * @return the value
     */
    public int getInt(String s) {
        return getInt(s, 0);
    }

    /**
     * get the config entry for string s, if no value is found
     * return the defaultValue specified.
     *
     * @param s string to get the value of
     * @param defaultValue Default value if entry is not found.
     * @return the value
     */
    public int getInt(String s, int defaultValue) {
        Integer val = getInteger(s);
        if (val == null) {
            return defaultValue;
        }
        return val;
    }

    /**
     * get the config entry for string s
     *
     * @param s string to get the value of
     * @return the value
     */
    public Integer getInteger(String s) {
        String val = getString(s);
        if (val == null) {
            return null;
        }
        return Integer.valueOf(val);
    }

    /**
     * get the config entry for string s, if no value is found
     * return the defaultValue specified.
     *
     * @param s string to get the value of
     * @param defaultValue Default value if entry is not found.
     * @return the value
     */
    public long getLong(String s, long defaultValue) {
        Long val = getLong(s);
        if (val == null) {
            return defaultValue;
        }
        return val;
    }

    /**
     * get the config entry for string s
     *
     * @param s string to get the value of
     * @return the value
     */
    public Long getLong(String s) {
        String val = getString(s);
        if (val == null) {
            return null;
        }
        return Long.valueOf(val);
    }

    /**
     * get the config entry for string s
     *
     * @param s string to get the value of
     * @return the value
     */
    public Float getFloat(String s) {
        String val = getString(s);
        if (val == null) {
            return null;
        }
        return Float.valueOf(val);
    }

    /**
     * get the config entry for string s, if no value is found
     * return the defaultValue specified.
     *
     * @param s string to get the value of
     * @param defaultValue Default value if entry is not found.
     * @return the value
     */
    public float getFloat(String s, float defaultValue) {
        Float val = getFloat(s);
        if (val == null) {
            return defaultValue;
        }
        return val;
    }

    /**
     * get the config entry for string s
     *
     * @param s string to get the value of
     * @return the value
     */
    public Double getDouble(String s) {
        String val = getString(s);
        if (val == null) {
            return null;
        }
        return Double.valueOf(val);
    }

    /**
     * get the config entry for string s, if no value is found return the defaultValue specified.
     *
     * @param s string to get the value of
     * @param defaultValue Default value if entry is not found.
     * @return the value
     */
    public double getDouble(String s, double defaultValue) {
        Double val = getDouble(s);
        if (val == null) {
            return defaultValue;
        }
        return val;
    }

    /**
     * Parses a comma-delimited list of values as a java.util.List
     * @param name config entry name
     * @return instance of java.util.List populated with config values
     */
    public List<String> getList(String name) {
        List<String> retval = new LinkedList<>();
        String[] vals = getStringArray(name);
        if (vals != null) {
            retval.addAll(Arrays.asList(vals));
        }
        return retval;
    }

    /**
     * get the config entry for string s
     *
     * @param s string to get the value of
     * @return the value
     */
    public String[] getStringArray(String s) {
        if (s == null) {
            return null;
        }
        String value = getString(s);

        if (value == null) {
            return null;
        }

        return value.split(",");
    }

    /**
     * get the config entry for string name
     *
     * @param name string to set the value of
     * @param value new value
     * @return the previous value of the property
     */
    public String setString(String name, String value) {
        return (String) configValues.setProperty(name, value);
    }

    /**
     * remove the config entry for key
     * @param name key to remove
     */
    public void remove(String name) {
        configValues.remove(name);
    }

    /**
     * get the config entry for string s or return false
     *
     * @param s string to get the value of
     * @return the value or false
     */
    public boolean getBoolean(String s) {
        return getBoolean(s, false);
    }

    /**
     * get the config entry for string s
     *
     * @param s string to get the value of
     * @param defaultValue the default value
     * @return the value
     */
    public boolean getBoolean(String s, boolean defaultValue) {
        String value = getString(s);
        LOGGER.debug("getBoolean() - {} is : {}", s, value);
        if (value == null) {
            return defaultValue;
        }

        //need to check the possible true values
        // tried to use BooleanUtils, but that didn't
        // get the job done for an integer as a String.
        for (String trueValue : TRUE_VALUES) {
            if (trueValue.equalsIgnoreCase(value)) {
                LOGGER.debug("getBoolean() - Returning true: {}", value);
                return true;
            }
        }

        return false;
    }

    /**
     * set the config entry for string name
     * @param s string to set the value of
     * @param b new value
     */
    public void setBoolean(String s, String b) {
        // need to check the possible true values
        // tried to use BooleanUtils, but that didn't
        // get the job done for an integer as a String.
        for (String trueValue : TRUE_VALUES) {
            if (trueValue.equalsIgnoreCase(b)) {
                configValues.setProperty(s, "1");

                // get out we're done here
                return;
            }
        }
        configValues.setProperty(s, "0");
    }

    private void getFiles(String location) {
        Path path = Path.of(location);
        if (Files.isDirectory(path) && Files.isReadable(path)) {
            try (Stream<Path> filesInDirectory = Files.list(path)) {
                PathMatcher configMatcher = path.getFileSystem().getPathMatcher("glob:*.conf");
                filesInDirectory
                    .filter(file -> Files.isRegularFile(file) && Files.isReadable(file))
                    .filter(file -> configMatcher.matches(file.getFileName()))
                    .forEach(fileList::add);
            }
            catch (IOException ex) {
                LOGGER.error("Unable to list files in directory {}", location);
            }
        }
        else if (Files.isRegularFile(path) && Files.isReadable(path)) {
            fileList.add(path);
        }
        else {
            LOGGER.warn("Ignoring path {} since it's not accessible", path);
        }
    }

    private String makeNamespace(Path f) {
        String ns = f.getFileName().toString();

        // This is really hokey, but it works. Basically, rhn.conf doesn't
        // match the standard rhn_foo.conf convention. So, to create the
        // namespace, we first special case rhn.*
        if (ns.startsWith("rhn.")) {
            return "";
        }

        ns = ns.replaceFirst("rhn_", "");
        int lastDotindex = ns.lastIndexOf('.');
        if (lastDotindex != -1) {
            ns = ns.substring(0, ns.lastIndexOf('.'));
        }
        ns = ns.replace("_", ".");
        return ns;
    }

    /**
     * Parse all of the added files.
     */
    public void parseFiles() {
        for (Path curr : fileList) {

            Properties props = new Properties();
            try {
                String configString = Files.readString(curr, StandardCharsets.UTF_8);
                props.load(new StringReader(configString.replace("\\", "\\\\")));
            }
            catch (IOException e) {
                LOGGER.error("Could not parse file {}", curr, e);
            }
            String ns = makeNamespace(curr);
            LOGGER.debug("Adding namespace: {} for file: {}", () -> ns, () -> curr.toAbsolutePath());

            // loop through all of the config values in the properties file
            // making sure the prefix is there.
            Properties newProps = new Properties();
            for (Object oIn : props.keySet()) {
                String key = (String) oIn;
                String newKey = key.startsWith(ns) ? key : ns + "." + key;

                LOGGER.debug("Adding: {}: {}", () -> newKey, () -> props.getProperty(key));
                newProps.put(newKey, props.getProperty(key));
            }
            configValues.putAll(newProps);
        }
    }

    /**
     * Returns a subset of the properties for the given namespace. This is
     * not a particularly fast method and should be used only at startup or
     * some other discreet time.  Repeated calls to this method are guaranteed
     * to be slow.
     *
     * @param namespace Namespace of properties to be returned.
     * @return subset of the properties that begin with the given namespace.
     */
    public Properties getNamespaceProperties(String namespace) {
        return getNamespaceProperties(namespace, namespace);
    }

    /**
     * Returns a subset of the properties for the given namespace. All the properties
     * will be moved to the specified new namespace. This is not a particularly
     * fast method and should be used only at startup or some other discreet time.
     * Repeated calls to this method are guaranteed to be slow.
     *
     * @param namespace Namespace of properties to be returned.
     * @param newNamespace the new namespace
     * @return subset of the properties that begin with the given namespace.
     */
    public Properties getNamespaceProperties(String namespace, String newNamespace) {
        final Properties prop = new Properties();
        for (Map.Entry<Object, Object> entry : configValues.entrySet()) {
            String key = (String) entry.getKey();
            if (key.startsWith(namespace)) {
                LOGGER.debug("Looking for key: [{}]", key);
                if (!namespace.equals(newNamespace)) {
                    key = key.replaceFirst(namespace, newNamespace);
                }

                prop.put(key, entry.getValue());
            }
        }

        return prop;
    }
}
