---
name: QE - Testing new operating systems
about: Use this template for manually testing new operating systems
title: "Test X on Y"
labels: ["qe-squad","manual tests"]
projects: ["SUSE/32"]
assignees: ''

---

# Description

This manually tests X for Y.

## Information

- Start date:
- End date:
- Environment:
- MLM version:
- Used environment:

---

## Tasks

### Prerequisites

- [ ] Wait until the submissions are ready
- [ ] Install and setup a testing environment with the correct version
- [ ] Verify if the new OS appear in the webUI under Admin -> Product Wizard
  - if not, get in touch with Orion so they reach out to SCC and make them available there, or we get a special
    `product-tree.json` file from Orion with the updated content that we can then use on our mirror. See [here](https://confluence.suse.com/x/t4C9dQ)
- [ ] Open the MLM documentation

### Tests

#### Prerequisites

- [ ] sync the product in the Product Wizard on your server
- [ ] create activation keys for Minion and SSH Minion
- [ ] install a VM from the official ISO for <>

#### Bootstrap via the [bootstrap script](https://documentation.suse.com/multi-linux-manager/5.1/en/docs/client-configuration/registration-bootstrap.html)

- [ ] onboard it as Minion
- [ ] do the [usual smoke tests](https://github.com/uyuni-project/uyuni/blob/master/testsuite/features/build_validation/smoke_tests/smoke_tests.template)

#### Bootstrap via the [webUI](https://documentation.suse.com/multi-linux-manager/5.1/en/docs/client-configuration/registration-webui.html)

- [ ] onboard it as SSH Minion
- [ ] do the [usual smoke tests](https://github.com/uyuni-project/uyuni/blob/master/testsuite/features/build_validation/smoke_tests/smoke_tests.template)
- [ ] delete it (and do a manual Salt cleanup on the VM)
- [ ] onboard it as Minion
- [ ] do the [usual smoke tests](https://github.com/uyuni-project/uyuni/blob/master/testsuite/features/build_validation/smoke_tests/smoke_tests.template)

### Special tasks

- [ ] ...

## Found issues/bugs

- ...

## Links

- [Client registration documentation](https://documentation.suse.com/multi-linux-manager/5.1/en/docs/client-configuration/registration-methods.html)
- ...

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
