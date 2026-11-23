import { useQuery } from '@tanstack/react-query'
import {
  FALLBACK_DOWNLOAD,
  FALLBACK_VERSION,
  REPO,
} from '../data/content'

export interface ReleaseInfo {
  version: string
  downloadUrl: string
  sizeLabel: string
  isLive: boolean
}

async function fetchRelease(): Promise<ReleaseInfo> {
  const response = await fetch(
    `https://api.github.com/repos/${REPO}/releases/latest`,
    { headers: { Accept: 'application/vnd.github+json' } },
  )
  if (!response.ok) throw new Error(`Release lookup failed: ${response.status}`)
  const data = (await response.json()) as {
    tag_name?: string
    assets?: Array<{ name: string; browser_download_url: string; size: number }>
  }
  const apk = data.assets?.find((asset) => asset.name.endsWith('.apk'))
  return {
    version: data.tag_name || FALLBACK_VERSION,
    downloadUrl: apk?.browser_download_url || FALLBACK_DOWNLOAD,
    sizeLabel: apk ? `${(apk.size / 1_048_576).toFixed(1)} MB` : '≈ 20 MB',
    isLive: true,
  }
}

/**
 * TanStack Query gives us caching, retries and a graceful offline fallback.
 * The download CTA is never blocked by the network.
 */
export function useRelease(): ReleaseInfo {
  const { data } = useQuery({
    queryKey: ['release', REPO],
    queryFn: fetchRelease,
    staleTime: 5 * 60 * 1000,
    retry: 1,
  })

  return (
    data ?? {
      version: FALLBACK_VERSION,
      downloadUrl: FALLBACK_DOWNLOAD,
      sizeLabel: '≈ 20 MB',
      isLive: false,
    }
  )
}
