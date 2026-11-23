# ROADMAP.md — Strategic SDLC for Farmerssteq Global

Delivery philosophy: **thin, lovable slices; proof before scale; seams before features.**

## Phase 0 — Foundation (done with v0.0.1)

- [x] Context, specs, architecture, security posture documented.
- [x] Toolchain verified (JDK 21, SDK 36, NDK 27, Gradle 9.9.2, Node).
- [x] Repository structure, version catalog, CI skeletons.
- **Gate:** a new contributor can build the app and site from the README.

## Phase 1 — Origin Agent v0.0.1 (ALP) — current

- [x] Native Android app: onboarding, home, notebook, **Solutions**, advisory, settings.
- [x] Material 3 Expressive identity, dynamic colour, micro-interactions, a11y.
- [x] C++17 deterministic core + JNI bridge.
- [x] Local-first persistence and JSON export.
- [x] Cloudflare Pages presentation/download site (`farmersteq-pages`).
- [x] GitHub Release automation via Fastlane + Actions.
- **Gate:** APK installs, core flow works offline, site links to working download.

## Phase 2 — Trust & distribution (v0.1.x)

- Signed release keystore in CI secrets; `app-release.apk` + AAB.
- Download manifest endpoint consumed by TanStack Query; version/badge shown on site.
- On-device language packs (first: Hausa, Yoruba, Igbo, Swahili).
- Crash/telemetry that is opt-in and anonymous by default.
- **Gate:** 100 real installs, ≥ 40% week-2 retention, zero P0 crashes.

## Phase 3 — Field intelligence (v0.2.x)

- Camera capture with on-device compression and attached photos.
- Real market price ingest from a documented source, with provenance UI.
- Weather-aware advisory using cached forecasts; still offline-first.
- Group/farm profiles for cooperatives.
- **Gate:** field agents log ≥ 10 notes/week without assistance.

## Phase 4 — Device-agnostic (v0.3.x → v1.0)

- KMP shared module; Compose Multiplatform desktop target for laptops.
- Adaptive list-detail navigation, keyboard shortcuts, larger canvases.
- Tablets and foldables as first-class layouts.
- **Gate:** same account-free core flow on Android and desktop with shared core tests.

## Phase 5 — Business layer (post-v1)

- Marketplace linkage, buyer leads, input ordering, financing partnerships.
- Role-based workspaces: farmer, aggregator, extension officer, buyer.
- measurable sustainable-future metrics (soil health, yield history, diversification).

## Release cadence

- `v0.0.x` — rapid ALP hardening, tag per working increment.
- `v0.x` — fortnightly, semantic versioning, release notes generated from PR labels.
- `v1.x` — monthly stable train with LTS tags for field deployments.

## Success metrics for v0.0.1

| Metric | Target |
| --- | --- |
| Core-flow completion after install (no help) | ≥ 80% of testers |
| Median time to first saved note | < 90 s |
| APK install size | < 25 MB |
| Offline availability of core features | 100% |
| Accessibility blockers | 0 |
| Security secrets in repo | 0 |
