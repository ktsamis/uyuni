---
name: QE - VM Images
about: Use this template for testing the VM images
title: "Test MLM VM Images installation for version "
labels: ["qe-squad","vm-images","manual tests"]
projects: ["SUSE/32"]
assignees: ''

---

## Information

- Planned start date:
- Real start date:
- Planned deadline:
- Real end date:
- Resubmissions:
- Submission card:

## Legend

- Selected checkbox means, we tested it
- :white_check_mark: : Test/verification was successful
- :x: : Test/verification was not successful
- :test_tube: : Test failed due to test suite issue but succeed manually
- If multiple emotes: task was run several times
  - Example: :x: :white_check_mark: = first run failed, second run passed (resubmission)

# Description

We will get new VM images together with version/MU <MU_number> and need to test
them. The installation of the server/proxy and doing a reposync after the
installation is enough.

## Tasks

- [ ] clarify, when we get the images to test and add the date in this card
  - releng/maintenance will ping us once available
- [ ] get the URL for the images and write it in this card
- [ ] x86
  - [ ] SelfInstall-Build
  - [ ] Raw-Build
  - [ ] VMWare (VBox)
- [ ] aarch64
  - [ ] SelfInstall-Build
  - [ ] Raw-Build
- [ ] s390
  - [ ]  qcow2 under KVM
  - [ ]  Raw DASD under z/VM
- [ ] PowerPC
  - [ ] Raw 4096
  - [ ] ISO self install 4096


 # Links
- https://confluence.suse.com/spaces/SUSEMANAGER/pages/1471348883/QE+VM+images
