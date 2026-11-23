import { Link } from '@tanstack/react-router'
import { QRCodeSVG } from 'qrcode.react'
import { ArrowIcon, CheckIcon, DownloadIcon, ShieldIcon } from '../components/Icons'
import { useRelease } from '../hooks/useRelease'

export function DownloadPage() {
  const release = useRelease()

  return (
    <section className="section download-page">
      <div className="wrap">
        <Link to="/" className="back-link"><ArrowIcon width={16} height={16} style={{ transform: 'rotate(180deg)' }} /> Back to overview</Link>

        <div className="download-hero">
          <span className="section-kicker">ANDROID · NATIVE APP</span>
          <h1>Download Farmerssteq Global</h1>
          <p>
            Version {release.version} · {release.sizeLabel} · native APK
            {release.isLive ? ' · live from GitHub Releases' : ' · fallback link shown while checking releases'}
          </p>
        </div>

        <div className="download-grid">
          <div className="download-card">
            <div className="download-qr">
              <QRCodeSVG value={release.downloadUrl} size={176} bgColor="#ffffff" fgColor="#0e3d2c" level="M" />
            </div>
            <h2>Scan with your phone</h2>
            <p>Open the camera on your Android device, point it at the code, then follow the download prompt.</p>
            <a className="btn btn-primary btn-wide" href={release.downloadUrl}>
              <DownloadIcon width={18} height={18} /> Download APK
            </a>
            <p className="fineprint">
              Direct link: <a href={release.downloadUrl}>farmsteq-global-{release.version}.apk</a>
            </p>
          </div>

          <div className="install-card">
            <h2>Install in three steps</h2>
            <ol>
              <li><span><CheckIcon width={16} height={16} /></span> Open the downloaded APK on your Android phone.</li>
              <li><span><CheckIcon width={16} height={16} /></span> Allow “Install unknown apps” for your browser or file manager when asked.</li>
              <li><span><CheckIcon width={16} height={16} /></span> Tap Install, open Farmerssteq, and log your first field observation.</li>
            </ol>

            <div className="notice">
              <ShieldIcon width={20} height={20} />
              <div>
                <strong>About this build</strong>
                <p>
                  v0.0.1 is a debug-signed Average Lovable Product for field trials. It needs no account,
                  no network permission and stores your notes only on your device. A signed production
                  release follows in Phase 2.
                </p>
              </div>
            </div>

            <div className="checksum-note">
              <strong>Verify the artifact</strong>
              <p>SHA-256 checksums are attached to every GitHub Release alongside the APK.</p>
              <a href={`https://github.com/${'SirCollins-Agency/FarmersteqGlobal-Solutions'}/releases/latest`}>
                View all releases <ArrowIcon width={14} height={14} />
              </a>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}
