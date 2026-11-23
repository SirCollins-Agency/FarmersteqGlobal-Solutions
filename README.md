# Farmsteq Global — v0.0.1 (Average Lovable Product)

> **Empowering Agricultural, Growing Business, Building Sustainable Future.**

Farmsteq Global is a mobile-first agricultural companion that turns a farmer's everyday observations into useful, context-aware decisions — offline, on a humble phone, in the language of the field.

This repository is the single source of truth for:

| Surface | Technology | Output |
| --- | --- | --- |
| **Origin Agent** (native Android app) | Kotlin + Jetpack Compose + Material 3 Expressive + C++17/NDK | installable `.apk` |
| **Presentation & download site** | Vite + React + TanStack Router/Query, Cloudflare Pages | `farmersteq.cculture.net` |
| **Release automation** | Fastlane + GitHub Actions | GitHub Release artifacts |
| **SDLC governance** | `CONTEXT.md`, `SPECS.md`, `ARCHITECTURE.md`, `ROADMAP.md` | repeatable delivery |

## Status

- **Version:** `0.0.1` (ALP — Average Lovable Product)
- **App:** native Android, minSdk 26, targetSdk 36, compileSdk 37.2 (ext 2)
- **Network:** not required for core use; all data is local-first
- **Verified:** debug APK builds, Kotlin unit tests and host-side C++ checks pass, presentation site builds and passes browser smoke test
- **Brand assets:** wordmark-first and vector-based; the supplied logo image could not be read by this model, so no bitmap logo is baked in. Drop the real logo into `site/public/brand/` and `app/src/main/res/drawable/` when available.

## Quick start

```bash
# 1. Native Android debug APK (requires ANDROID_HOME=/opt/android-sdk in this environment)
export ANDROID_HOME=/opt/android-sdk
./gradlew :app:assembleDebug
# → app/build/outputs/apk/debug/app-debug.apk
# On hosts where the x86_64 NDK cannot execute (e.g. arm64 Linux), add -PskipNative=true.

# 2. Verify
./scripts/test-cpp.sh
./gradlew :app:testDebugUnitTest

# 3. Presentation site (local preview)
cd site && npm install && npm run dev

# 4. Static site build (Cloudflare Pages output)
cd site && npm run build
# → site/dist
```

See [`SPECS.md`](SPECS.md#commands) for the full command matrix and [`ROADMAP.md`](ROADMAP.md) for the phased delivery plan.

## Download

The v0.0.1 APK is attached to the [GitHub Release](https://github.com/SirCollins-Agency/FarmsteqGlobal-Solutions/releases/latest) as `farmsteq-global-v0.0.1.apk`.
The public download page is served from [`farmersteq.cculture.net`](https://farmersteq.cculture.net) (Cloudflare Pages project `farmersteq-pages`).

## Core experience in v0.0.1

1. **Onboarding** — three expressive steps, usable immediately, no account.
2. **Home** — contextual greeting, one actionable "Today's focus", quick actions.
3. **Field Notebook** — capture crop, growth stage, condition and notes; stored locally.
4. **Solutions** — a structured toolkit (Field Intelligence, Advisory, Market Signals, Business Growth, Sustainability) with honest maturity labels and a bundled sample price board.
5. **Advisory** — rule- and C++-core-driven recommendations derived from what you logged.
6. **Settings** — theme/dynamic colour, local data export, contact and about.

## Documentation

- [`CONTEXT.md`](CONTEXT.md) — product context, stakeholders, constraints, definition of "done enough".
- [`SPECS.md`](SPECS.md) — functional/non-functional specs, skills, tools, workflows, quality gates.
- [`ARCHITECTURE.md`](ARCHITECTURE.md) — system design, rationale, replaceability and device-agnostic path.
- [`ROADMAP.md`](ROADMAP.md) — strategic SDLC phases and release gates.
- [`SECURITY.md`](SECURITY.md) — credential hygiene, signing, secrets, disclosure.

## Credits

Proposed by **Sir Collins Tech-Agency**. Client: **Hon. Sanda Nathan — CEO, Farmsteq Global**.

Contact: [nathansandajnr@gmail.com](mailto:nathansandajnr@gmail.com) · [Facebook](https://www.facebook.com/share/1CqqtKZKo9/)
