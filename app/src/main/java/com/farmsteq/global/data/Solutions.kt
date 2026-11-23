package com.farmsteq.global.data

/**
 * The Solutions pillar: a structured catalogue of what Farmsteq delivers.
 * Each track has an honest maturity label so the product never overpromises.
 */
enum class SolutionStatus(val label: String) {
    AVAILABLE("Available now"),
    PREVIEW("Preview"),
    PLANNED("Planned"),
}

enum class SolutionIcon { NOTEBOOK, ADVISORY, MARKET, BUSINESS, SUSTAINABILITY }

data class SolutionTrack(
    val id: String,
    val title: String,
    val summary: String,
    val status: SolutionStatus,
    val icon: SolutionIcon,
)

val solutionTracks: List<SolutionTrack> = listOf(
    SolutionTrack(
        id = "notebook",
        title = "Field intelligence",
        summary = "Capture crops, stages and observations offline, then see them organised in one notebook.",
        status = SolutionStatus.AVAILABLE,
        icon = SolutionIcon.NOTEBOOK,
    ),
    SolutionTrack(
        id = "advisory",
        title = "Practical advisory",
        summary = "On-device calculations turn your notes into one calm, actionable next step.",
        status = SolutionStatus.AVAILABLE,
        icon = SolutionIcon.ADVISORY,
    ),
    SolutionTrack(
        id = "market",
        title = "Market signals",
        summary = "A bundled price board to start comparing offers. Live, sourced prices arrive in v0.2.",
        status = SolutionStatus.PREVIEW,
        icon = SolutionIcon.MARKET,
    ),
    SolutionTrack(
        id = "business",
        title = "Business growth",
        summary = "Buyer leads, input planning and cooperative workspaces are on the roadmap.",
        status = SolutionStatus.PLANNED,
        icon = SolutionIcon.BUSINESS,
    ),
    SolutionTrack(
        id = "sustainability",
        title = "Sustainable future",
        summary = "Long-term soil and yield history that makes progress visible over seasons.",
        status = SolutionStatus.PLANNED,
        icon = SolutionIcon.SUSTAINABILITY,
    ),
)
