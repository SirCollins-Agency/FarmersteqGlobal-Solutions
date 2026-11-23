import type { SVGProps } from 'react'
import type { Solution } from '../data/content'

type IconProps = SVGProps<SVGSVGElement>

const base = (props: IconProps) => ({
  width: 24,
  height: 24,
  viewBox: '0 0 24 24',
  fill: 'none',
  stroke: 'currentColor',
  strokeWidth: 1.8,
  strokeLinecap: 'round' as const,
  strokeLinejoin: 'round' as const,
  ...props,
})

export const SproutIcon = (props: IconProps) => (
  <svg {...base(props)}>
    <path d="M12 21v-8" />
    <path d="M12 13C8 13 5 10 4 6c4 0 7 2 8 7Z" />
    <path d="M12 12c4-1 6-4 7-8-4 0-7 3-7 8Z" />
  </svg>
)

export const ArrowIcon = (props: IconProps) => (
  <svg {...base(props)}>
    <path d="M5 12h14m-6-6 6 6-6 6" />
  </svg>
)

export const DownloadIcon = (props: IconProps) => (
  <svg {...base(props)}>
    <path d="M12 3v12m-5-5 5 5 5-5" />
    <path d="M4 17v2a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2v-2" />
  </svg>
)

export const ShieldIcon = (props: IconProps) => (
  <svg {...base(props)}>
    <path d="M12 3 4.5 6v5c0 4.6 3.1 8.4 7.5 10 4.4-1.6 7.5-5.4 7.5-10V6L12 3Z" />
    <path d="m9 12 2 2 4-4" />
  </svg>
)

export const OfflineIcon = (props: IconProps) => (
  <svg {...base(props)}>
    <path d="m3 3 18 18" />
    <path d="M8.5 8.5A7 7 0 0 0 5 10.8M12 6.5a12 12 0 0 1 7 3" />
    <path d="M9 15a4 4 0 0 1 6 0" />
    <path d="M12 19h.01" />
  </svg>
)

export const MailIcon = (props: IconProps) => (
  <svg {...base(props)}>
    <rect x="3" y="5" width="18" height="14" rx="2" />
    <path d="m3 7 9 6 9-6" />
  </svg>
)

export const FacebookIcon = (props: IconProps) => (
  <svg {...base(props)} stroke="none" fill="currentColor">
    <path d="M13.5 21v-8h2.7l.4-3.1h-3.1V7.9c0-.9.25-1.5 1.55-1.5H17V3.6A22 22 0 0 0 14.35 3.5c-2.45 0-4.15 1.5-4.15 4.25v2.15H7.5V13h2.7v8h3.3Z" />
  </svg>
)

export const CheckIcon = (props: IconProps) => (
  <svg {...base(props)}>
    <path d="m5 12 4.5 4.5L19 7" />
  </svg>
)

export const Glyph = ({ name, ...props }: IconProps & { name: Solution['glyph'] }) => {
  switch (name) {
    case 'notebook':
      return (
        <svg {...base(props)}>
          <rect x="5" y="3" width="14" height="18" rx="2" />
          <path d="M9 8h6M9 12h6M9 16h4" />
        </svg>
      )
    case 'advisory':
      return (
        <svg {...base(props)}>
          <path d="M9 18h6M10 21h4" />
          <path d="M12 3a6 6 0 0 0-3.5 10.9c.6.5.9 1.2 1 2h5c.1-.8.4-1.5 1-2A6 6 0 0 0 12 3Z" />
        </svg>
      )
    case 'market':
      return (
        <svg {...base(props)}>
          <path d="M4 19V9m5 10V5m6 14v-7m5 7V8" />
          <path d="M3 21h18" />
        </svg>
      )
    case 'business':
      return (
        <svg {...base(props)}>
          <rect x="3" y="8" width="18" height="12" rx="2" />
          <path d="M8 8V6a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2M3 13h18" />
        </svg>
      )
    case 'sustainability':
      return (
        <svg {...base(props)}>
          <path d="M20 4c-9 0-14 4-14 10a6 6 0 0 0 6 6c6 0 8-7 8-16Z" />
          <path d="M4 21c2-5 6-8 11-10" />
        </svg>
      )
  }
}
