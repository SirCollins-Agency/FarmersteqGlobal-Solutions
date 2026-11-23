import { Link } from '@tanstack/react-router'
import { EMAIL, FACEBOOK, SLOGAN } from '../data/content'
import { FacebookIcon, MailIcon, SproutIcon } from './Icons'

export function SiteFooter() {
  return (
    <footer className="site-footer">
      <div className="wrap footer-grid">
        <div>
          <div className="brand footer-brand">
            <span className="brand-mark"><SproutIcon width={22} height={22} /></span>
            <span className="brand-text">
              <strong>farmsteq</strong>
              <small>G L O B A L</small>
            </span>
          </div>
          <p className="footer-slogan">{SLOGAN}</p>
        </div>

        <div className="footer-col">
          <h4>Product</h4>
          <Link to="/">Overview</Link>
          <a href="/#solutions">Solutions</a>
          <Link to="/download">Download APK</Link>
        </div>

        <div className="footer-col">
          <h4>Connect</h4>
          <a href={`mailto:${EMAIL}`}><MailIcon width={16} height={16} /> {EMAIL}</a>
          <a href={FACEBOOK} target="_blank" rel="noreferrer"><FacebookIcon width={16} height={16} /> Facebook page</a>
        </div>

        <div className="footer-col">
          <h4>Credits</h4>
          <p>Proposed by Sir Collins Tech-Agency</p>
          <p>Client: Hon. Sanda Nathan — CEO</p>
        </div>
      </div>

      <div className="wrap footer-bottom">
        <span>© 2026 Farmerssteq Global · v0.0.1</span>
        <span>Built native for Android · Ready for tomorrow’s devices</span>
      </div>
    </footer>
  )
}
