package com.farmsteq.global

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.farmsteq.global.core.FarmCore
import com.farmsteq.global.data.AppPreferences
import com.farmsteq.global.data.CropStage
import com.farmsteq.global.data.FieldNote
import com.farmsteq.global.data.FileLocalStore
import com.farmsteq.global.data.LocalStore
import com.farmsteq.global.data.newNoteId
import com.farmsteq.global.domain.AdvisoryEngine
import com.farmsteq.global.domain.CppAdvisoryEngine
import com.farmsteq.global.domain.Focus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class UiState(
    val loading: Boolean = true,
    val onboarded: Boolean = false,
    val notes: List<FieldNote> = emptyList(),
    val theme: String = "system",
    val dynamicColor: Boolean = true,
    val focus: Focus = Focus("", "", "", 0),
    val nativeCoreAvailable: Boolean = FarmCore.isNativeAvailable,
    val message: String? = null,
)

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val store: LocalStore = FileLocalStore(app.applicationContext)
    private val advisory: AdvisoryEngine = CppAdvisoryEngine()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _exportJson = MutableStateFlow<String?>(null)
    val exportJson: StateFlow<String?> = _exportJson.asStateFlow()

    init {
        viewModelScope.launch {
            store.initialize()
            combine(store.notes, store.preferences) { notes, prefs ->
                Triple(notes, prefs, advisory.focus(notes))
            }.collect { (notes, prefs, focus) ->
                _state.value = _state.value.copy(
                    loading = false,
                    onboarded = prefs.onboarded,
                    notes = notes,
                    theme = prefs.theme,
                    dynamicColor = prefs.dynamicColor,
                    focus = focus,
                )
            }
        }
    }

    fun completeOnboarding() = updatePrefs { copy(onboarded = true) }

    fun addNote(crop: String, stage: CropStage, condition: Int, plotHa: Double, note: String) {
        val entry = FieldNote(
            id = newNoteId(),
            crop = crop.trim().ifBlank { "Unspecified crop" },
            stage = stage,
            condition = condition.coerceIn(0, 100),
            plotHa = plotHa.coerceAtLeast(0.1),
            note = note.trim(),
            createdAt = System.currentTimeMillis(),
        )
        viewModelScope.launch {
            store.addNote(entry)
            _state.value = _state.value.copy(message = "Field note saved on this device")
        }
    }

    fun adviceFor(note: FieldNote): String = advisory.advice(note)

    fun deleteNote(id: String) = viewModelScope.launch {
        store.deleteNote(id)
        _state.value = _state.value.copy(message = "Note removed")
    }

    fun setTheme(theme: String) = updatePrefs { copy(theme = theme) }

    fun setDynamicColor(enabled: Boolean) = updatePrefs { copy(dynamicColor = enabled) }

    fun requestExport() = viewModelScope.launch {
        _exportJson.value = store.snapshotJson()
    }

    fun clearExport() { _exportJson.value = null }

    fun consumeMessage() { _state.value = _state.value.copy(message = null) }

    private fun updatePrefs(transform: AppPreferences.() -> AppPreferences) = viewModelScope.launch {
        val current = _state.value
        store.setPreferences(
            AppPreferences(
                onboarded = current.onboarded,
                theme = current.theme,
                dynamicColor = current.dynamicColor,
            ).transform()
        )
    }
}
