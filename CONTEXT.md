# CONTEXT.md — Farmerssteq Global

*The durable context every future agent, engineer and reviewer reads first. If a decision conflicts with this file, fix the decision or amend this file deliberately.*

## 1. Identity

| Field | Value |
| --- | --- |
| Product | Farmerssteq Global |
| Slogan | Empowering Agricultural, Growing Business, Building Sustainable Future. |
| Proposer | Sir Collins Tech-Agency |
| Client | Hon. Sanda Nathan — CEO, Farmerssteq Global |
| Origin Agent | **Native Android app** (explicitly not a PWA) |
| Release target | v0.0.1 — "Average Lovable Product" (ALP) |
| GitHub owner/repo | `SirCollins-Agency` / `FarmersteqGlobal-Solutions` |
| Cloudflare Pages project | `farmersteq-pages` |
| Public host | `farmersteq.cculture.net` |
| Contact | nathansandajnr@gmail.com · https://www.facebook.com/share/1CqqtKZKo9/ |

## 2. What "Average Lovable Product" means here

An ALP is not a demo and not a full platform. It is the smallest release that a real smallholder or field agent can **love using for a week** because it is genuinely useful on day one. It must:

- install and run without an account, network connection or configuration;
- do a small number of things extremely well rather than many things shallowly;
- feel premium and calm on a low-end device;
- be honest about its limits (no invented live data, no fake AI claims);
- leave a clean seam for everything that comes next.

## 3. Non-negotiables (from the brief)

Beautiful, powerful, elegant, intuitive, out-of-the-box, interactive, responsive, accessible, context-aware **without being intrusive**, consistent across devices, useful immediately, graceful under constrained resources, genuine usefulness over decorative complexity.

Additional lenses: architecture, viability, modularity, interoperability, scalability, replaceability, beyond-state-of-the-art craft, premium quality.

## 4. Design principles (how we resolve trade-offs)

1. **Native first, portable by construction.** Android is the first target. UI is declarative Compose; business logic is kept out of Activities; the C++ core is pure and platform-neutral. A future Kotlin Multiplatform / desktop target reuses logic, not layouts.
2. **Local-first truth.** The device is the source of truth for the farmer's own records. Network features are additive and clearly labelled. No spinner blocks a field note.
3. **Expressive, not loud.** Material You dynamic colour where available, Material 3 Expressive shapes and motion, generous touch targets, meaningful micro-interactions, reduced-motion support.
4. **Every dependency must earn its place.** We deliberately choose a short list of excellent technologies (Kotlin/Compose, C++17, TanStack, Fastlane, Cloudflare) and go deep rather than wide.
5. **Replaceability over lock-in.** Interfaces (`LocalStore`, `AdvisoryEngine`, `PriceSource`) are small and swappable. Nothing in the UI imports a storage SDK.
6. **Honesty over theatre.** Bundled prices say "sample data". The C++ engine is a transparent deterministic rules engine, not marketed as AI.
7. **Secrets never live in the repository.** See `SECURITY.md`.

## 5. Stakeholders and jobs to be done

| Stakeholder | Job | v0.0.1 response |
| --- | --- | --- |
| Smallholder farmer | Remember what happened in the field and get one next action | Field Notebook + Today's Focus |
| Field agent / extension worker | Capture observations quickly offline | One-screen note capture, local export |
| Agribusiness / buyer | Understand supply context | Solutions → Market Signals (seed data now, real feed later) |
| CEO / proposer | A credible, beautiful product proof point | Publishable APK + presentation site + release |
| Future engineering team | Change one thing without breaking everything | Modular boundaries, ADRs, specs, CI gates |

## 6. Scope of v0.0.1

**In:** onboarding, home focus card, field notebook CRUD, **Solutions module** (structured toolkit + bundled market signals), advisory rules, settings (theme + export + about/contact), adaptive icon, accessibility, local persistence, C++ core, CI, GitHub Release, Cloudflare Pages site.

**Out:** accounts/auth, payments, chat, map layers, camera pipeline, push notifications, real market API, offline map tiles, iOS/desktop binaries, translations beyond English. These are sequenced in `ROADMAP.md`.

## 7. Environment facts observed at implementation time

- JDK 21, Android SDK platform 36 / build-tools 36.0.0, NDK 27.3.13750724, CMake 3.22.1, Gradle 9.9.2 — all available locally.
- Node/npm available; `gh`, `wrangler`, Ruby/Fastlane are **not** preinstalled — CI installs them.
- Workspace started empty; the logo image was not machine-readable by the implementing model.
- Brand must therefore be vector/wordmark-first with a clearly marked slot for the official logo.

## 8. Definition of "done enough" for v0.0.1

- `./gradlew :app:assembleDebug` succeeds from a clean checkout with documented SDK setup.
- Core flow works with airplane mode on: onboard → log note → see advisory → export data.
- `npm run build` in `site/` produces a static `dist/` deployable to Cloudflare Pages.
- CI attaches a versioned APK and SHA-256 checksum to a GitHub Release.
- No secret, keystore or personal token is present in git history.
- Every screen has content descriptions, supports dynamic type and respects reduced motion.
- Docs answer "why", "how", "what next" without a meeting.
