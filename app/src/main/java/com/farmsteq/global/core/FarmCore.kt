package com.farmsteq.global.core

/**
 * Thin, safe boundary around the deterministic C++17 core.
 *
 * The native library is optional at runtime: if it cannot be loaded (for example on an
 * unusual ABI), Kotlin fallbacks keep the Origin Agent useful. This is the "graceful
 * operation under constrained resources" rule made concrete.
 */
object FarmCore {

    private val loaded: Boolean = runCatching { System.loadLibrary("farmsteq-core") }.isSuccess

    val isNativeAvailable: Boolean get() = loaded

    private external fun nativeEstimateYieldKg(areaHa: Double, health: Double, cropFactor: Double): Double
    private external fun nativeWaterNeedLitres(areaHa: Double, etoMm: Double, cropFactor: Double): Double
    private external fun nativeFocusScore(stage: Int, condition: Int, daysSince: Int): Int
    private external fun nativeRecommendation(
        crop: String,
        stage: Int,
        condition: Int,
        daysSince: Int,
        areaHa: Double,
    ): String

    /** Mirrors the core's crop coefficients so the UI can label calculations honestly. */
    fun cropFactor(crop: String): Double = when (crop.trim()) {
        "Maize" -> 1.05
        "Rice" -> 1.25
        "Cassava" -> 0.85
        "Yam" -> 0.95
        "Sorghum" -> 0.80
        "Groundnut" -> 0.75
        else -> 0.90
    }

    fun estimateYieldKg(areaHa: Double, health: Double, cropFactor: Double): Double =
        if (loaded) nativeEstimateYieldKg(areaHa, health, cropFactor)
        else (1800.0 * areaHa.coerceAtLeast(0.0) * cropFactor.coerceAtLeast(0.1) *
            (0.35 + 0.65 * (health / 100.0).coerceIn(0.0, 1.0)))

    fun waterNeedLitres(areaHa: Double, etoMm: Double, cropFactor: Double): Double =
        if (loaded) nativeWaterNeedLitres(areaHa, etoMm, cropFactor)
        else areaHa.coerceAtLeast(0.0) * 10_000.0 * etoMm.coerceAtLeast(0.0) * cropFactor.coerceAtLeast(0.1)

    fun focusScore(stage: Int, condition: Int, daysSince: Int): Int =
        if (loaded) nativeFocusScore(stage, condition, daysSince)
        else {
            val stageWeight = when (stage) { 0 -> 9; 1 -> 17; 2 -> 20; 3 -> 14; else -> 12 }
            (stageWeight + (100 - condition) / 2 + daysSince.coerceAtMost(14) * 3).coerceIn(0, 100)
        }

    fun recommendation(
        crop: String,
        stage: Int,
        condition: Int,
        daysSince: Int,
        areaHa: Double,
    ): String = if (loaded) {
        runCatching { nativeRecommendation(crop, stage, condition, daysSince, areaHa) }
            .getOrElse { fallbackRecommendation(stage, condition, daysSince) }
    } else {
        fallbackRecommendation(stage, condition, daysSince)
    }

    private fun fallbackRecommendation(stage: Int, condition: Int, daysSince: Int): String = when {
        condition < 35 -> "Walk the worst-looking rows first. Check moisture at root depth before you apply anything."
        daysSince >= 7 -> "It has been a while since this field was checked. Make one short pass today."
        stage == 2 -> "Flowering is the sensitive stage: keep moisture steady and avoid heavy work today."
        stage == 0 -> "Seedling stage: check for gaps and pest damage early."
        else -> "Crop looks steady. Keep your routine pass and record any change you notice."
    }
}
