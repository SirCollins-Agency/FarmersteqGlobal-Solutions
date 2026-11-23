# SPECS.md — Farmerssteq Global

Implementation contract: skills, stack, features, workflows and quality gates. Version targeted: **0.0.1**.

## 1. Functional specification

| ID | Requirement | Acceptance |
| --- | --- | --- |
| FR-01 | First-run onboarding | Three steps; skippable; choice persisted locally; no account. |
| FR-02 | Home focus | Greeting by time of day + one ranked action from the advisory engine. |
| FR-03 | Field note capture | Create/edit/delete a note with crop, stage, condition (0–100), date and text. Works offline. |
| FR-04 | Local persistence | Notes and preferences survive process death and reboot. Atomic file replacement. |
| FR-05 | Advisory engine | Deterministic recommendation from crop stage, condition and elapsed days, computed by the C++ core. |
| FR-06 | Solutions module | Structured toolkit catalogue with honest maturity labels (Available/Preview/Planned) plus a bundled commodity price board explicitly marked as sample data. |
| FR-07 | Data export | Export all notes as JSON via the OS share sheet / clipboard fallback. |
| FR-08 | Settings | Theme: system/light/dark; dynamic colour toggle (Android 12+); about, contact, Facebook links. |
| FR-09 | Accessibility | All interactive elements labelled; 48dp minimum targets; scalable fonts; reduced-motion aware. |
| FR-10 | Presentation site | Beautiful responsive single-page site describing the product and offering the APK download. |
| FR-11 | Distribution | GitHub Release with versioned APK + checksum; Cloudflare Pages site at project `farmersteq-pages`. |

## 2. Non-functional specification

| ID | Requirement | Target |
| --- | --- | --- |
| NFR-01 | Cold start | < 2 s on a mid-range device (no work on main thread beyond compose). |
| NFR-02 | Offline | 100% of FR-01…FR-09 available with no network. |
| NFR-03 | Footprint | Debug APK < 25 MB; no bitmap assets in v0.0.1. |
| NFR-04 | Resilience | Malformed/partial local JSON degrades to empty state, never a crash. |
| NFR-05 | Accessibility | TalkBack traversal order correct; contrast ≥ 4.5:1 for body text. |
| NFR-06 | Maintainability | No Activity > 120 lines; no UI class imports storage implementations. |
| NFR-07 | Determinism | C++ core has no clock, locale or I/O dependencies; identical inputs → identical outputs. |
| NFR-08 | Security | No secrets in repo; exported data contains only farmer-entered content. |
| NFR-09 | Replaceability | Storage, pricing and advisory each behind a Kotlin interface. |
| NFR-10 | Consistency | Shared design tokens between app and site (green/teal family, radii, type scale). |

## 3. Technology choices (deliberately few, deliberately deep)

| Layer | Choice | Why |
| --- | --- | --- |
| Native UI | Kotlin 2.4.21 + Jetpack Compose, Material 3 1.5.0 (Expressive) | Best-in-class declarative native Android; Material You theming; micro-interactions without hand-rolled view code. |
| Build | Gradle 9.9.2 + Android Gradle Plugin 9.5.2 | Current stable, matches installed SDK 36/NDK 27. |
| Domain core | C++17 via CMake/NDK 27 | Deterministic, testable, reusable beyond Android; no JNI in UI layer. |
| Local data | kotlinx.serialization JSON + atomic `filesDir` write | Zero annotation processors, tiny, transparent, replaceable by Room/SQLite later. |
| Web/presentation | Vite + TypeScript + React + **TanStack Router + TanStack Query** + `qrcode.react` | Type-safe routing and server-state discipline even on a static site; natural home for a future download/API manifest. |
| Hosting | Cloudflare Pages project `farmersteq-pages` | Fast global static delivery, preview deployments, custom domain `farmerssteq.cculture.net`. |
| Mobile CI/CD | **Fastlane** + GitHub Actions | One command locally and in CI for build/sign/release; `set_github_release` uploads artifacts. |
| Quality | Gradle unit tests + CTest + TypeScript `tsc --noEmit` + CI | Deterministic gates, no flaky device matrix in v0.0.1. |

Versions are pinned in `gradle/libs.versions.toml` and `site/package.json`. Upgrade one axis at a time, in a dedicated PR.

## 4. Skill map (what each contributor/agent must be able to do)

| Skill | Owner | Evidence |
| --- | --- | --- |
| Compose/Material 3 craft | Mobile | Screens use tokens, semantics, reduced motion; no hardcoded colours. |
| Native build & signing | Mobile/Release | Gradle tasks green; keystore only in CI secrets. |
| C++/JNI discipline | Core | Pure functions + JNI bridge isolated in one file; CTest green. |
| TanStack web engineering | Web | Type-safe routes; query client used for release manifest. |
| Cloudflare Pages ops | Web/Release | `wrangler pages deploy` works; custom domain documented. |
| Fastlane release engineering | Release | `fastlane android release` produces the same artifact CI does. |
| Secure secret handling | All | Rotation policy; no token in files/history/logs. |
| Documentation as code | All | CONTEXT/SPECS/ADRs updated in the same PR as behaviour. |

## 5. Repository layout

```text
.
├── app/                     native Android module (Kotlin + Compose + cpp/)
├── site/                    TanStack/React presentation + download site
├── fastlane/                Fastfile, Appfile
├── .github/workflows/       build, release, pages deploy
├── gradle/                  version catalog + wrapper
├── CONTEXT.md  SPECS.md  ARCHITECTURE.md  ROADMAP.md  SECURITY.md
└── wrangler.toml            Cloudflare Pages project config
```

## 6. Workflows

### 6.1 Local development loop

1. Read `CONTEXT.md` and this file.
2. Create branch `feature/<slug>` or `fix/<slug>`.
3. Change code **and** the relevant spec/ADR in the same PR.
4. Run `./gradlew :app:testDebugUnitTest :app:assembleDebug` and `cd site && npm run check && npm run build`.
5. Self-review with the quality gates below; open PR.

### 6.2 Release workflow (v0.0.1)

1. Tag `v0.0.1` on `main`.
2. `android-release.yml` builds the APK, renames it `farmerssteq-global-v0.0.1.apk`, computes SHA-256, creates/updates the GitHub Release and uploads both files.
3. `pages-deploy.yml` builds `site/` and deploys to Cloudflare Pages project `farmersteq-pages` on `main`.
4. Bind `farmerssteq.cculture.net` in Cloudflare (DNS/hostnames) and verify HTTPS.
5. Smoke test: download APK from the site, install on a device, complete the core flow.

### 6.3 Fastlane parity

```bash
bundle install
fastlane android trial      # local debug APK
fastlane android verify     # C++ checks + unit tests
fastlane android release tag:v0.0.1   # uses GH_TOKEN from environment/CI secrets
```

## 7. Quality gates (must pass before merge)

- [ ] `./gradlew :app:testDebugUnitTest` green
- [ ] `./gradlew :app:assembleDebug` green
- [ ] C++ core checks pass via `./scripts/test-cpp.sh` (and CMake/`externalNativeBuild` compiles on x86_64 CI)
- [ ] `cd site && npm run check && npm run build` green
- [ ] No new `TODO` without an issue reference
- [ ] No secrets, personal data or absolute local paths in diff
- [ ] Accessibility: new UI has `contentDescription`/`text` semantics and 48dp targets
- [ ] Docs updated when behaviour or architecture changed

## 8. Commands

| Task | Command |
| --- | --- |
| Clean + build APK | `./gradlew clean :app:assembleDebug` (add `-PskipNative=true` if the host cannot execute the NDK toolchain) |
| Unit tests | `./gradlew :app:testDebugUnitTest` |
| C++ core checks | `./scripts/test-cpp.sh` |
| Install to connected device | `./gradlew :app:installDebug` |
| Site dev server | `cd site && npm run dev` |
| Site typecheck/build | `cd site && npm run check && npm run build` |
| Deploy site (needs `CLOUDFLARE_API_TOKEN`) | `npx wrangler pages deploy site/dist --project-name farmersteq-pages` |
| Release (needs `GH_TOKEN`) | `bundle exec fastlane android release` |

## 9. Decision log pointers

Architectural rationale lives in `ARCHITECTURE.md` §5 (ADR-01…ADR-06). Strategic sequencing lives in `ROADMAP.md`.
