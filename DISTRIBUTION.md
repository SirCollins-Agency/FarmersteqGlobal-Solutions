# DISTRIBUTION.md — Farmerssteq Global

## Live channels

| Channel | Location | Status |
| --- | --- | --- |
| GitHub Release (primary artifact) | `SirCollins-Agency/FarmersteqGlobal-Solutions` → `v0.0.1` | **Live** |
| Direct APK | https://github.com/SirCollins-Agency/FarmersteqGlobal-Solutions/releases/latest/download/farmerssteq-global-v0.0.1.apk | **Live** (HTTP 200) |
| SHA-256 checksum | `farmerssteq-global-v0.0.1.apk.sha256` on the release | **Live** |
| Presentation/download site | Cloudflare Pages project `farmersteq-pages` | Pending deploy |
| Public hostname | `farmerssteq.cculture.net` | Pending binding |
| QR + install guide | `/download` on the Pages site | Ready in `site/dist` |
| CI auto-release on `v*` tag | `.github/workflows/android-release.yml` | Ready |
| Fastlane manual release | `fastlane android release tag:v0.0.1` | Ready |

## Release details

- **Tag:** `v0.0.1`
- **Asset:** `farmerssteq-global-v0.0.1.apk` (20,541,967 bytes)
- **Build:** debug-signed trial APK, `minSdk 26`, `targetSdk 36`
- **SHA-256:** `f9af76ee4d451fde730b15f01d59acc91224373bd794ff0a40c7c21020997e87`
- **Release page:** https://github.com/SirCollins-Agency/FarmersteqGlobal-Solutions/releases/tag/v0.0.1

## Verify a download

```bash
curl -LO https://github.com/SirCollins-Agency/FarmersteqGlobal-Solutions/releases/latest/download/farmerssteq-global-v0.0.1.apk
curl -LO https://github.com/SirCollins-Agency/FarmersteqGlobal-Solutions/releases/latest/download/farmerssteq-global-v0.0.1.apk.sha256
sha256sum -c farmerssteq-global-v0.0.1.apk.sha256
```

## Publishing a new version

1. Bump `versionCode`/`versionName` in `app/build.gradle.kts` and `site/package.json`.
2. Run `./gradlew :app:assembleDebug :app:testDebugUnitTest` and `./scripts/test-cpp.sh`.
3. Run `cd site && npm run build`.
4. Commit and push to `main`.
5. Tag and push `vX.Y.Z` — CI creates the GitHub Release with APK and checksum.
6. Deploy the site with `npx wrangler pages deploy site/dist --project-name farmersteq-pages`.

## Credentials

See `SECURITY.md`. The GitHub PAT used for this session was shared in chat and must be rotated. CI uses `GITHUB_TOKEN`, `CLOUDFLARE_API_TOKEN` and `CLOUDFLARE_ACCOUNT_ID` from GitHub Actions secrets; no token values are committed.
