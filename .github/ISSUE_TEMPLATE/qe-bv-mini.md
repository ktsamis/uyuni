---
name: QE - Mini Build Validation
about: QE - Use this template for a the automated mini BV
title: "Automated mini BV of X on Y"
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

The purpose of this mini BV is to test the installation and the set up of the server and proxy on our
second supported host OS (SL Micro).

## Preparation

- [ ] JSON creation (go through the JSON file before using it)
- [ ] deployment

## Proxy

- [ ] Proxy setup

## Client Bootstrap and Smoke Tests

- [ ] Client bootstrap stage
- [ ] Client smoke tests

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
