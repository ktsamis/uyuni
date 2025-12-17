---
name: SUSE Multi-Linux Manager MU release
about: Use this template for SUSE Multi-Linux Manager MU releases (announcements)
title: 'X.Y.Z Maintenance Update release'
labels: ["vega-squad"]
projects: ["SUSE/35"]
assignees: ''

---

Related: #

A couple of days before the release deadline:

- [ ] Ensure that the documentation team integrates the most recent updates into the master branch and the appropriate version directory at the [documentation.suse.com repository](https://gitlab.suse.de/susedoc/docserv-external-tree-suma). This process should encompass all translations, except in cases where an exemption has been explicitly granted.

Release deadline:

- [ ] Approve all RRs and ping the Maintenance Team, so they can release
- [ ] Ping doc team at `#team-multi-linux-manager-docs` and ask them to publish the latest documentation build(s) for the given MU at documentation.suse.com
- [ ] Run `osc -A https://api.suse.de pr SUSE:Containers:SUSE-Manager:X.Y` (replace `X.Y` to see if the containers are published)
- [ ] Wait a couple of hours and announce to the `multi-linux-manager@suse.de` mailing list. See example email https://mailman.suse.de/mlarch/SuSE/suse-manager/2021/suse-manager.2021.12/msg00028.html
- [ ] Create requests for maintainership for the new packages with `osc -A https://api.suse.de bugowner -S group:<GROUP> -m "Add maintainer" <CODESTREAM>/<PACKAGE>` (this must be done **AFTER** the release)
