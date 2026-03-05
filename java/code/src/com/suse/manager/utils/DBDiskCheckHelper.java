/*
 * Copyright (c) 2021 SUSE LLC
 *
 * This software is licensed to you under the GNU General Public License,
 * version 2 (GPLv2). There is NO WARRANTY for this software, express or
 * implied, including the implied warranties of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. You should have received a copy of GPLv2
 * along with this software; if not, see
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt.
 */

package com.suse.manager.utils;

import com.redhat.rhn.common.hibernate.HibernateFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Class to execute a check of the available disk space using a sql function
 */
public class DBDiskCheckHelper extends DiskCheckHelper {

    private static final Logger LOG = LogManager.getLogger(DBDiskCheckHelper.class);

    /**
     * Executes the SQL function and returns the result as an exit code.
     * @return the value returned from the database query.
     * @throws IOException when https://bugzilla.suse.com/show_bug.cgi?id=1253153 a database error occurs.
     */
    @Override
    protected int performCheck() throws IOException, InterruptedException {
        return HibernateFactory.getSession()
        .createNativeQuery("SELECT get_pgsql_disk_severity()", Integer.class)
        .getSingleResult();
    }
}
