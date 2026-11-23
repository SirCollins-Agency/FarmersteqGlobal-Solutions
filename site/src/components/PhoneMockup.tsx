import { SproutIcon } from './Icons'

/** Pure-CSS device frame: no screenshots, no bitmaps, scales cleanly. */
export function PhoneMockup() {
  return (
    <div className="phone-stage" aria-hidden="true">
      <div className="phone-glow" />
      <div className="phone">
        <div className="phone-notch" />
        <div className="phone-screen">
          <div className="mini-status">
            <span>9:41</span>
            <span className="mini-dots">● ● ●</span>
          </div>

          <div className="mini-topbar">
            <div className="mini-brand">
              <span className="mini-brand-mark">
                <SproutIcon width={14} height={14} />
              </span>
              <span>farmsteq</span>
            </div>
            <span className="mini-avatar">SN</span>
          </div>

          <div className="mini-greeting">
            <span className="mini-kicker">TODAY’S FOCUS</span>
            <strong>Maize · Vegetative</strong>
            <p>Keep soil moisture steady. Walk the worst-looking rows first, then log what you find.</p>
            <div className="mini-progress"><span /></div>
            <div className="mini-progress-label"><span>Focus score</span><b>72</b></div>
          </div>

          <div className="mini-actions">
            <div className="mini-action"><i>✎</i><span>Log note</span></div>
            <div className="mini-action"><i>✦</i><span>Advisory</span></div>
            <div className="mini-action"><i>◫</i><span>Solutions</span></div>
          </div>

          <div className="mini-section">RECENT OBSERVATIONS</div>
          <div className="mini-note">
            <span className="mini-note-icon">V</span>
            <span><b>Maize · Vegetative</b><small>Health 78% · Today</small></span>
          </div>
          <div className="mini-note">
            <span className="mini-note-icon mint">F</span>
            <span><b>Cassava · Flowering</b><small>Health 64% · Yesterday</small></span>
          </div>

          <div className="mini-nav">
            <span className="active">⌂<small>Home</small></span>
            <span>▤<small>Notebook</small></span>
            <span>◫<small>Solutions</small></span>
            <span>✦<small>Advisory</small></span>
          </div>
        </div>
      </div>
    </div>
  )
}
