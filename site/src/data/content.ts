export const SLOGAN =
  'Empowering Agriculture, Growing Businesses, Building Sustainable Futures'

export const REPO = 'SirCollins-Agency/FarmersteqGlobal-Solutions'
export const FALLBACK_VERSION = 'v0.0.1'
export const FALLBACK_DOWNLOAD = `https://github.com/${REPO}/releases/latest/download/farmerssteq-global-v0.0.1.apk`
export const FACEBOOK = 'https://www.facebook.com/share/1CqqtKZKo9/'
export const EMAIL = 'nathansandajnr@gmail.com'

export type TrackStatus = 'Available now' | 'Preview' | 'Planned'

export interface Solution {
  id: string
  title: string
  summary: string
  status: TrackStatus
  glyph: 'notebook' | 'advisory' | 'market' | 'business' | 'sustainability'
}

export const solutions: Solution[] = [
  {
    id: 'field-intelligence',
    title: 'Field Intelligence',
    summary:
      'Capture crops, growth stages and observations in a notebook that works without signal and stays on the device.',
    status: 'Available now',
    glyph: 'notebook',
  },
  {
    id: 'practical-advisory',
    title: 'Practical Advisory',
    summary:
      'On-device calculations turn yesterday’s notes into one calm, actionable focus for today.',
    status: 'Available now',
    glyph: 'advisory',
  },
  {
    id: 'market-signals',
    title: 'Market Signals',
    summary:
      'A clearly-labelled sample price board to start comparing offers. Live, sourced prices arrive in v0.2.',
    status: 'Preview',
    glyph: 'market',
  },
  {
    id: 'business-growth',
    title: 'Business Growth',
    summary:
      'Buyer leads, input planning and cooperative workspaces are sequenced on the roadmap.',
    status: 'Planned',
    glyph: 'business',
  },
  {
    id: 'sustainable-future',
    title: 'Sustainable Future',
    summary:
      'Long-term soil and yield history that makes progress across seasons visible and measurable.',
    status: 'Planned',
    glyph: 'sustainability',
  },
]

export const principles = [
  { title: 'Offline-first', body: 'Your notebook never waits for a network.' },
  { title: 'On-device core', body: 'Deterministic C++ logic, computed locally.' },
  { title: 'No account needed', body: 'Useful from the very first minute.' },
  { title: 'Material You', body: 'Expressive, accessible, beautifully native.' },
]

export const steps = [
  {
    n: '01',
    title: 'Walk your field',
    body: 'Notice what is changing. Farmerssteq asks for just a crop, a stage and how it looks.',
  },
  {
    n: '02',
    title: 'Save one observation',
    body: 'A single screen, no signal required. Your note is stored safely on the device.',
  },
  {
    n: '03',
    title: 'Get today’s focus',
    body: 'A practical next step appears on the home screen — one thing, done well.',
  },
]
