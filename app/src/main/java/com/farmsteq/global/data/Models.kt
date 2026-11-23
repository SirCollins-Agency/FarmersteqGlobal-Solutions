package com.farmsteq.global.data

import kotlinx.serialization.Serializable

@Serializable
enum class CropStage(val label: String, val code: Int) {
    SEEDLING("Seedling", 0),
    VEGETATIVE("Vegetative", 1),
    FLOWERING("Flowering", 2),
    MATURE("Mature", 3),
}

@Serializable
data class FieldNote(
    val id: String,
    val crop: String,
    val stage: CropStage,
    val condition: Int,
    val plotHa: Double,
    val note: String,
    val createdAt: Long,
)

@Serializable
data class AppPreferences(
    val onboarded: Boolean = false,
    val theme: String = "system", // system | light | dark
    val dynamicColor: Boolean = true,
)

@Serializable
internal data class PersistedState(
    val notes: List<FieldNote> = emptyList(),
    val preferences: AppPreferences = AppPreferences(),
)
