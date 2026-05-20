---
name: QE - Long-running environment upgrade
about: QE - Upgrade of the long-running environment
title: "Upgrade the X long-running environment"
labels: ["qe-squad", "build validation", "long-running environment"]
projects: ["SUSE/32"]
assignees: ''

---

## Information

- Start date:
- End date:
- Server URL:

---

The official documentation is linked below, but you need to run the `upgrade` command with the correct MI container URL to get the correct container to update to.

### Upgrade before the release

- [ ] Upgrade the server
- [ ] Upgrade the proxy

### Upgrade after the release

- [ ] Upgrade the server
- [ ] Upgrade the proxy

---

## Links

- [MLM server upgrade docs](https://documentation.suse.com/multi-linux-manager/5.1/en/docs/installation-and-upgrade/container-management/updating-server-containers.html)
- [MLM proxy upgrade docs](https://documentation.suse.com/multi-linux-manager/5.1/en/docs/installation-and-upgrade/container-management/updating-proxy-containers.html)
- [Uyuni upgrade docs](https://www.uyuni-project.org/uyuni-docs/en/uyuni/installation-and-upgrade/container-management/updating-server-containers.html)
- Uyuni related information in [Confluence](https://confluence.suse.com/x/hAChew)

## Legend

- Selected checkbox means, we tested it and the testing is completed with no
  pending blockers to be verified as fixed in a resubmission
- :white_check_mark: : Test/verification was successful
- :x: : Test/verification was not successful
- :test_tube: : Test failed due to test suite issue but succeed manually
- If multiple emotes: task was run several times
  - Example: :x: :white_check_mark: = first run failed, second run passed (resubmission)
  - Example: In case of a blocker, we should wait for the fix and retest -> :x:
    and not checked checkbox. In case of non-blocker -> :x: and checked
    checkbox.
