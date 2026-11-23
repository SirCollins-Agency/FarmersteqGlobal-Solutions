package com.farmsteq.global.domain

import com.farmsteq.global.core.FarmCore
import com.farmsteq.global.data.FieldNote

data class Focus(
    val title: String,
    val body: String,
    val actionLabel: String,
    val score: Int,
    val noteId: String? = null,
)

interface AdvisoryEngine {
    fun focus(notes: List<FieldNote>): Focus
    fun advice(note: FieldNote): String
}

/** Deterministic, offline, and explainable. Backed by the C++ core with Kotlin fallback. */
class CppAdvisoryEngine : AdvisoryEngine {

    override fun focus(notes: List<FieldNote>): Focus {
        val latest = notes.maxByOrNull { it.createdAt }
            ?: return Focus(
                title = "Your first field note",
                body = "Open the notebook and record what you see in one field. Everything else starts there.",
                actionLabel = "Log an observation",
                score = 0,
            )

        val days = ((System.currentTimeMillis() - latest.createdAt) / 86_400_000L)
            .toInt().coerceAtLeast(0)
        val score = FarmCore.focusScore(latest.stage.code, latest.condition, days)
        val body = FarmCore.recommendation(
            crop = latest.crop,
            stage = latest.stage.code,
            condition = latest.condition,
            daysSince = days,
            areaHa = latest.plotHa,
        ).trim()

        return Focus(
            title = "${latest.crop} · ${latest.stage.label}",
            body = body,
            actionLabel = "Review in notebook",
            score = score,
            noteId = latest.id,
        )
    }

    override fun advice(note: FieldNote): String = FarmCore.recommendation(
        crop = note.crop,
        stage = note.stage.code,
        condition = note.condition,
        daysSince = ((System.currentTimeMillis() - note.createdAt) / 86_400_000L).toInt().coerceAtLeast(0),
        areaHa = note.plotHa,
    ).trim()
}
