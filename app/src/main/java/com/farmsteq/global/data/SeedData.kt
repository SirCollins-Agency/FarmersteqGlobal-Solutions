package com.farmsteq.global.data

/**
 * Bundled, clearly-labelled sample prices. v0.0.1 makes no claim of live market data;
 * this seam is replaced by a real, provenance-tagged PriceSource in v0.2.
 */
data class MarketItem(
    val commodity: String,
    val pricePerKg: Double,
    val changePercent: Int,
    val note: String,
)

val sampleMarket: List<MarketItem> = listOf(
    MarketItem("Maize", 245.0, 3, "Grain · stable demand"),
    MarketItem("Cassava", 132.0, -2, "Roots · bulk buyers"),
    MarketItem("Yam", 410.0, 5, "Tubers · seasonal peak"),
    MarketItem("Rice (paddy)", 318.0, 1, "Grain · mills active"),
    MarketItem("Sorghum", 198.0, 0, "Grain · breweries"),
    MarketItem("Groundnut", 465.0, 4, "Oilseed · kernels"),
)

const val MARKET_SAMPLE_LABEL = "Sample data · bundled with v0.0.1"
