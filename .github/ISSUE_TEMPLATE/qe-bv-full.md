---
name: QE - Full Build Validation
about: QE - Use this template for a the automated full BV
title: "Automated full BV of X on Y"
labels: ["qe-squad", "build validation"]
projects: ["SUSE/32"]
assignees: ''

---

## Information

- Start date:
- End date:
- Resubmissions:

---

- Jenkins pipeline:
- Server URL:

---

## Links

- [Step-by-step guide](https://confluence.suse.com/display/SUSEMANAGER/QE+Build+Validation)
- [Automated tests](https://confluence.suse.com/display/SUSEMANAGER/Automated+tests)
- [Pipeline parameters](https://confluence.suse.com/display/SUSEMANAGER/The+new+BV+pipeline)

---

## Preparation

- [ ] Check the mirror
- [ ] JSON creation (go through the JSON file before using it)

## Proxy and Monitoring

- [ ] Proxy setup
- [ ] Monitoring server setup

## Client Bootstrap and Smoke Tests

- [ ] All supported systems were bootstrapped and passed smoke tests
  - [ ] Client bootstrap stage
  - [ ] Client smoke tests

## Migration Tests

- [ ] Product and Salt migration tests
  - [ ] SLES 15 SP6 minion → SP7
  - [ ] SLES 15 SP6 SSH minion → SP7
  - [ ] SL Micro 5.5 → 6.1
  - [ ] OS Salt → Salt Bundle

## Retail

- [ ] SLES 15 SP6
- [ ] SLES 15 SP7

---

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
