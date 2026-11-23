import { Link } from '@tanstack/react-router'
import { DownloadIcon, Glyph, OfflineIcon, ShieldIcon, SproutIcon } from '../components/Icons'
import { PhoneMockup } from '../components/PhoneMockup'
import { principles, SLOGAN, solutions, steps } from '../data/content'
import { useRelease } from '../hooks/useRelease'

export function HomePage() {
  const release = useRelease()

  return (
    <>
      <section className="hero" id="overview">
        <div className="hero-orb hero-orb-one" />
        <div className="hero-orb hero-orb-two" />
        <div className="wrap hero-grid">
          <div className="hero-copy">
            <span className="eyebrow"><span className="pulse" /> Origin Agent · {release.version} · Native Android</span>
            <h1>
              Farming, with a<br />
              <em>clearer next step.</em>
            </h1>
            <p className="hero-lead">{SLOGAN}</p>
            <p className="hero-body">
              Farmsteq turns the observations you already make — a crop, a stage, a change in the leaves —
              into a beautiful offline notebook and one practical focus for today.
            </p>

            <div className="hero-buttons">
              <a className="btn btn-primary" href={release.downloadUrl}>
                <DownloadIcon width={18} height={18} /> Download for Android
              </a>
              <a className="btn btn-ghost" href="#solutions">
                Explore Solutions
              </a>
            </div>

            <div className="hero-meta">
              <span><ShieldIcon width={16} height={16} /> No account</span>
              <span><OfflineIcon width={16} height={16} /> Works offline</span>
              <span><SproutIcon width={16} height={16} /> {release.sizeLabel} APK</span>
            </div>
          </div>

          <PhoneMockup />
        </div>
      </section>

      <section className="strip">
        <div className="wrap strip-grid">
          {principles.map((item) => (
            <div className="strip-item" key={item.title}>
              <strong>{item.title}</strong>
              <span>{item.body}</span>
            </div>
          ))}
        </div>
      </section>

      <section className="section" id="solutions">
        <div className="wrap">
          <div className="section-head">
            <div>
              <span className="section-kicker">THE TOOLKIT</span>
              <h2>Solutions, not promises.</h2>
            </div>
            <p>
              Every capability is labelled with its real maturity. What is ready today is genuinely useful;
              what comes next is honestly mapped.
            </p>
          </div>

          <div className="solution-grid">
            {solutions.map((solution) => (
              <article className={`solution-card status-${solution.status.toLowerCase().replaceAll(' ', '-')}`} key={solution.id}>
                <div className="solution-icon"><Glyph name={solution.glyph} /></div>
                <span className={`badge badge-${solution.status.toLowerCase().replaceAll(' ', '-')}`}>
                  {solution.status}
                </span>
                <h3>{solution.title}</h3>
                <p>{solution.summary}</p>
              </article>
            ))}
          </div>
        </div>
      </section>

      <section className="section section-tint" id="how">
        <div className="wrap">
          <div className="section-head">
            <div>
              <span className="section-kicker">HOW IT WORKS</span>
              <h2>Useful in under a minute.</h2>
            </div>
            <p>No onboarding marathon. No configuration screen. Open the app and begin.</p>
          </div>

          <div className="steps">
            {steps.map((step) => (
              <div className="step" key={step.n}>
                <span className="step-number">{step.n}</span>
                <h3>{step.title}</h3>
                <p>{step.body}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      <section className="download-band">
        <div className="wrap band-inner">
          <div>
            <span className="section-kicker">READY WHEN YOU ARE</span>
            <h2>Put the field in your pocket.</h2>
            <p>Download the v0.0.1 ALP build and record your first observation today.</p>
          </div>
          <div className="band-actions">
            <a className="btn btn-light" href={release.downloadUrl}>
              <DownloadIcon width={18} height={18} /> Download APK
            </a>
            <Link className="btn btn-outline-light" to="/download">
              Scan & install
            </Link>
          </div>
        </div>
      </section>
    </>
  )
}
