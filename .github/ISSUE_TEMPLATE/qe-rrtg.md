---
name: QE - RRTG
about: Use this template for the RRTG role
title: "RRTG Week "
labels: ["qe-squad", "testsuite review", "testsuite"]
projects: ["SUSE/32"]
assignees: ''

---

### 💥 Blockers

*Blockers found in a CI of a branch that impedes to submit that branch*

**Version reviewed to be submitted:** 4.x / 5.x / Uyuni

- Keep in mind that depending on the week, we need to be more focused on some branches due to MU submissions being prepared.
- Please, check the [calendar](https://confluence.suse.com/display/SUSEMANAGER/Release+calendar) and edit the field above.

**Lists of blockers:**

- [ ] 🛑 Short description
  - Bugzilla link:
  - Related card:
- [ ] 🛑 Short description
  - Bugzilla link:
  - Related card:
- [ ] 🛑 Short description
  - Bugzilla link:
  - Related card:

**Notes:**

- Add additional blockers to the list following the same format
- Duplicate this section if you review multiple branches whom must be submitted during your card.
- As soon as a blocker is resolved, mark it on the list. This list must be updated ASAP, release engineers are monitoring it. Furthermore, we need to track all the issues that could delay a submission
- Add additional information to a blocker in a new comment below and keep that list clean and simple

---

### ℹ️ Useful information

- **[SUMA Test suite status board](https://github.com/orgs/SUSE/projects/23/views/3)**

- **Reminders**:
  - Update the topic in #team-susemanager with your name
  - Edit the title of the card to include the week numbers
  - Review the test report and compare failures within the issues in the [test suite board](https://github.com/orgs/SUSE/projects/23/views/3)
  - Create new cards for each new issue following this title format: `Feature <title> | Scenario <name>`
  - Label the cards with the version the issue was found for: "xxx_ci", i.e. `4.3_ci`
  - You can also compare the new failures with older versions from the [Grafana Test Report timeline (Features/Version)](http://grafana.mgr.suse.de/d/GreziyMMk/testsuites-wip-time-perspective?orgId=1&from=now-3d&to=now)
  - See the [RRTG introduction](https://confluence.suse.com/display/SUSEMANAGER/The+Round+Robin+Testsuite+Geeko) for more information on the role
  - It's recommended to add a pipeline report about the number of failures in each pipeline with icons green, red to show if they pass in secondary and arrows to show the trend of failures since the day before in the #discuss-multi-linux-manager-ci-development channel in slack.

- **Links to the test suites**:
  - [Head Podman](https://ci.suse.de/view/Manager/view/Manager-Head/job/manager-Head-dev-acceptance-tests-podman/)
  - [Head RKE2](https://ci.suse.de/view/Manager/view/Manager-Head/job/manager-Head-dev-acceptance-tests-RKE2/)
  - [5.1](https://ci.suse.de/view/Manager/view/Manager-5.1/job/manager-5.1-dev-acceptance-tests/)
  - [5.0](https://ci.suse.de/view/Manager/view/Manager-5.0/job/manager-5.0-dev-acceptance-tests/),[5.0 Backup](https://ci.suse.de/view/Manager/view/Manager-5.0/job/manager-5.0-dev-acceptance-tests-BACKUP/)
  - [4.3](https://ci.suse.de/view/Manager/view/Manager-4.3/job/manager-4.3-dev-acceptance-tests/), [4.3 Backup](https://ci.suse.de/view/Manager/view/Manager-4.3/job/manager-4.3-dev-acceptance-tests-BACKUP/)
  - [Uyuni](https://ci.suse.de/view/Manager/view/Uyuni/job/uyuni-master-dev-acceptance-tests-podman/)
  - [Uyuni RKE2](https://ci.suse.de/view/Manager/view/Uyuni/job/uyuni-master-dev-acceptance-tests-RKE2/)
  - [Github acceptance tests on master](https://github.com/uyuni-project/uyuni/actions/workflows/acceptance_tests_scheduler.yml)
