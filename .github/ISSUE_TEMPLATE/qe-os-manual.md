---
name: QE - Testing new operating systems
about: Use this template for testing new operating systems
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
- [ ] Open the MLM documentation

### Tests

- [ ] sync the product in the Product Wizard on your server
- [ ] create activation keys for Minion and SSH Minion
- [ ] install a VM from the official ISO for <>
- [ ] onboard it as Minion
- [ ] do the usual smoke tests
- [ ] delete it and do some Salt cleanup on the VM
- [ ] onboard it as SSH Minion
- [ ] do the usual smoke tests

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
