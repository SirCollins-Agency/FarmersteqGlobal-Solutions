import { Link } from '@tanstack/react-router'
import { SproutIcon } from './Icons'

export function SiteHeader() {
  return (
    <header className="site-header">
      <div className="wrap header-inner">
        <Link to="/" className="brand" aria-label="Farmsteq Global home">
          <span className="brand-mark"><SproutIcon width={22} height={22} /></span>
          <span className="brand-text">
            <strong>farmsteq</strong>
            <small>G L O B A L</small>
          </span>
        </Link>

        <nav className="header-nav" aria-label="Main navigation">
          <a href="/#overview">Overview</a>
          <a href="/#solutions">Solutions</a>
          <a href="/#how">How it works</a>
          <Link to="/download">Download</Link>
        </nav>

        <Link to="/download" className="btn btn-small btn-primary header-cta">
          Get the app
        </Link>
      </div>
    </header>
  )
}
