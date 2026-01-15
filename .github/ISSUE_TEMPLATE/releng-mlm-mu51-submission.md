---
name: SUSE Multi-Linux Manager 5.1 MU submission
about: Use this template for SUSE Multi-Linux Manager 5.1 MU submissions
title: 'X.Y.Z Maintenance Update submission'
labels: ["vega-squad", "5.1"]
projects: ["SUSE/35"]
assignees: ''

---

# Important changes, for the release notes

-

# Deadlines

Check the [Release Calendar](https://confluence.suse.com/display/SUSEMANAGER/Release+calendar)
Freeze:
Submission:
Release:

# Procedure

https://confluence.suse.com/display/SUSEMANAGER/Maintenance+Update+procedure

# Needed issues or EPICs to be included in this version

Add any additional bugzilla report, PR, or EPIC that must be included in this release.

You can also create sub-issues if preferred, but the needed information must be mentioned in this section for having an overview of still pending changes that could block the release.

-

# TODO some days before

Add more tasks if needed.

- [ ] Review the content of the channel files
- [ ] Lock the branches
  - [ ] [Edit the branch protection rule](https://github.com/SUSE/spacewalk/settings/branch_protection_rules/65952452) for the `Manager-5.1` branch  on `SUSE/spacewalk`.
    - [ ] Ensure that the checkbox `Restrict who can push to matching branches` is marked
    - [ ] Rename the `Branch name pattern` from `BRANCH-TO-BE-LOCKED-FOR-MU-51` to `Manager-5.1` and save the rule
  - [ ] [Edit the branch protection rule](https://github.com/SUSE/uyuni-tools/settings/branch_protection_rules/65956485) for the branch `Manager-5.1` on `SUSE/uyuni-tools`.
    - [ ] Ensure that the checkbox `Restrict who can push to matching branches` is marked
    - [ ] Rename the `Branch name pattern` from `BRANCH-TO-BE-LOCKED-FOR-MU-51` to `Manager-5.1` and save the rule
- [ ] Send an email to galaxy-devel@suse.de informing that the branches `SUSE/spacewalk:Manager-5.1` and `SUSE/uyuni-tools:Manager-5.1` are locked, and adjust the topic on the slack channel [#team-multi-linux-manager](https://suse.slack.com/archives/C02D78LLS04)
- [ ] Ask the Doc Squad to submit an update of the doc package to `Devel:Galaxy:Manager:5.1` project, and ask them to warn the translators so they can start their work. A Merge Request for the [documentation.suse.com repository](https://gitlab.suse.de/susedoc/docserv-external-tree-suma) should get created a few days before the release
- [ ] After the branch freeze, ask [Orion](https://suse.slack.com/archives/C02DDMY6R0R) to prepare the PR for the code translations. If nobody from Orion is available, ask @parlt91. They will add us as reviewers.
- [ ] Increase `web.version` in `web/conf/rhn_web.conf` (`x.y.z+1`) in `Manager-5.1` branch
  - [ ] Check that the sed that replaces the default tag for SUSE Multi-Linux Manager in the [push.sh](https://github.com/SUSE/uyuni-tools/blob/Manager-5.1/push.sh) script in [uyuni-tools](https://github.com/SUSE/uyuni-tools) specifies the same version you added in `web.version`. If the value is outdated, update it with a PR for [uyuni-tools](https://github.com/SUSE/uyuni-tools). For example, see the [PR done](https://github.com/SUSE/uyuni-tools/pull/43) for fixing the default pull tag after the release of SUSE Manager 5.0.2.
- [ ] Check if the migration paths exist for both the main database and report database, if they are needed (more at https://confluence.suse.com/display/SUSEMANAGER/Maintenance+Update+procedure)
- [ ] Check if schema migration directories exist between older and newer SUSE Manager version (more at https://confluence.suse.com/display/SUSEMANAGER/Maintenance+Update+procedure)
- [ ] Push changes to `Manager-5.1` branch
- [ ] Create the JIRA ticket, with placeholders for the IDs (this is needed by ION squad too)
- [ ] Confirm that the SR for the documentation is merged, and translators warned
- [ ] Merge the PR for the translations with the option `Merge pull request`
- [ ] Ask the Ion Squad to promote salt and bundle versions where needed (not needed for MUs without salt/client tools). Remember that we have an **[agreement on how and when to submit salt](https://docs.google.com/document/d/140wx3yvFk1klXiHMzBI-cKf4s-Z2nzycGWS2KC5qUvA)**.
  - [ ] When the promotion is done, ask Ion to submit classic salt only to `SUSE:SLE-15-SP7:Update:Products:MultiLinuxManager51:Update`. Tell them to add to the submit message the ID of the Jira ticket in the form `ijsc#MSQA-$ID`, for example, `ijsc#MSQA-808`
  - [ ] remember that relengs submit the bundle to the 5.1 client tools while the classic salt submission is handled by the Ion squad
  - [ ] Add all the IDs (and notes, if any), to the JIRA ticket, and ping the Maintenance Team at [#discuss-susemamanager-maintenance](https://app.slack.com/client/T02863RC2AC/C02DEF2U0E5)
- [ ] Quick review changelogs with `tito-wrapper`, and request changes if needed
- [ ] Check all the relevant [5.1 CI tests for the 5.1 version](https://ci.suse.de/view/Manager/view/Manager-5.1/): everything should be green, or otherwise submission must be approved by the RRTG

# TODO during the submission window

Add more tasks if needed (for example, asking Maintenace to change the channel definitions).

- [ ] Check all the relevant [5.1 CI tests for the 5.1 version](https://ci.suse.de/view/Manager/view/Manager-5.1/): everything should be green, or otherwise submission must be approved by the RRTG
- [ ] Tag everything with `tito tag`. Do **NOT** tag if the version has not been already bumped in `web/conf/rhn_web.conf` (`web.version.uyuni`), as requested in one of the previous points.
- [ ] Check that the job [manager-5.1-releng-2obs](https://ci.suse.de/view/Manager/view/Manager-5.1/job/manager-5.1-releng-2obs/) and [manager-5.1-uyunitools-2obs](https://ci.suse.de/view/Manager/view/Manager-5.1/job/manager-5.1-uyunitools-2obs/) have packaged and submitted the changes from `Manager-5.1` branch into the relevant https://build.suse.de/project/subprojects/Devel:Galaxy:Manager:5.1 project.
- [ ] Prepare the submissions with `mu-massive-task` or `patch-creator`. For any new packages that will be added to the codestreams, fetch the groups what will maintain them (one per package) and document this at the release card.
- [ ] For submitting to **SL Micro 6.0** the client tools and salt bundle, remember that you can't use `patch-creator`; instead use the script `submit-slmicro6_clienttools` that will explain step by step what to submit and how.
- [ ] Add all the IDs (and notes, if any), to the JIRA ticket, and ping the Maintenance Team at [#discuss-susemamanager-maintenance](https://app.slack.com/client/T02863RC2AC/C02DEF2U0E5)
- [ ] Once autobuild approves all MRs, create the `Manager-5.1-MU-X.Y.Z` branch and push it.
- [ ] Before unlocking the branches `SUSE/spacewalk:Manager-5.1` and `SUSE/uyuni-tools:Manager-5.1`, consider PRs for merge that became ready during the branch freeze looking for the ["merge-candidate" label](https://github.com/SUSE/spacewalk/pulls?q=is%3Apr+is%3Aopen+label%3Amerge-candidate) or ["merge-candidate" label for uyuni-tools](https://github.com/SUSE/uyuni-tools/pulls?q=is%3Apr+is%3Aopen+label%3Amerge-candidate) or ping reviewers to take care of it.
- [ ] Unlock the branches
  - [ ] [Edit the branch protection rule](https://github.com/SUSE/spacewalk/settings/branch_protection_rules/65952452) for the `Manager-5.1` branch  on `SUSE/spacewalk`.
    - [ ] Ensure that the checkbox `Restrict who can push to matching branches` is marked
    - [ ] Rename the `Branch name pattern` from `Manager-5.1` to `BRANCH-TO-BE-LOCKED-FOR-MU-51` and save the rule
  - [ ] [Edit the branch protection rule](https://github.com/SUSE/uyuni-tools/settings/branch_protection_rules/65956485) for the branch `Manager-5.1` on `SUSE/uyuni-tools`.
    - [ ] Ensure that the checkbox `Restrict who can push to matching branches` is marked
    - [ ] Rename the `Branch name pattern` from `Manager-5.1` to `BRANCH-TO-BE-LOCKED-FOR-MU-51` and save the rule
- [ ] Send an email to galaxy-devel@suse.de informing that the branches `SUSE/spacewalk:Manager-5.1` and `SUSE/uyuni-tools:Manager-5.1` are unlocked , and adjust the topic on the slack channel [#team-multi-linux-manager](https://suse.slack.com/archives/C02D78LLS04)
- [ ] Add links to the patchinfos on top of this issue, and send for the PO with a link to this issue, and the deadline for the SR for the release notes.
- [ ] Submit the release notes
- [ ] Create the GitHub issues for the submission and release of the next MU.
