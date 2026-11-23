# SECURITY.md — Farmerssteq Global

## 1. Credential hygiene (read this first)

During the brief, a **Cloudflare API token** and a **GitHub Personal Access Token** were pasted into a chat. Any secret shared in a conversation must be considered exposed.

**Required actions after this session:**

1. Revoke/rotate the pasted Cloudflare token and GitHub PAT in their respective dashboards.
2. Create least-privilege replacements:
   - Cloudflare: Pages edit + Account read, scoped to the `farmersteq-pages` project where possible.
   - GitHub: fine-grained PAT with Contents (read/write) and Releases (write) on `FarmersteqGlobal-Solutions` only.
3. Store replacements only in GitHub Actions secrets / a password manager. Never in source, docs, logs, screenshots or chat.
4. If the exposed tokens appear anywhere in git history, rotate again and treat history as compromised.

The implementation deliberately contains **no token values**. CI references variable names only:

| Secret | Used by | Purpose |
| --- | --- | --- |
| `GH_TOKEN` | `android-release.yml`, Fastlane | Create GitHub Release and upload APK/checksum. |
| `CLOUDFLARE_API_TOKEN` | `pages-deploy.yml` | Deploy `site/dist` to Cloudflare Pages. |
| `CLOUDFLARE_ACCOUNT_ID` | `pages-deploy.yml` | Account selector (non-secret identifier, stored as secret for convenience). |
| `ANDROID_KEYSTORE_BASE64` | signing step (Phase 2) | Release signing keystore. |
| `ANDROID_KEYSTORE_PASSWORD`, `ANDROID_KEY_ALIAS`, `ANDROID_KEY_PASSWORD` | signing step (Phase 2) | Keystore credentials. |

## 2. v0.0.1 signing posture

The v0.0.1 artifact is built with the **debug keystore** and is clearly labelled as an ALP test build. It is suitable for field trials, not for long-term production distribution: Android will not guarantee upgrades from a debug-signed build to a differently-signed release. Phase 2 introduces a managed release keystore and version bump strategy.

## 3. Data handling

- Farmer notes never leave the device unless the farmer explicitly exports them.
- No analytics, no advertising SDKs, no device identifiers in v0.0.1.
- Export contains only farmer-entered fields and app version; no tokens or paths.
- Local store writes to app-private storage (`filesDir`), not external storage.

## 4. Supply chain

- Pin Gradle, AGP, Kotlin and npm versions; commit lockfile.
- Enable Dependabot for Gradle and npm.
- CI runs with least-privilege `GITHUB_TOKEN` unless a release needs `GH_TOKEN`.
- Third-party actions pinned to major tags; review diffs on upgrades.

## 5. Responsible disclosure

Email **nathansandajnr@gmail.com** with a description, reproduction and impact. Do not open a public issue for an unpatched vulnerability. We aim to acknowledge within 72 hours.
