package com.farmsteq.global.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.UUID

/** Small, replaceable boundary. A future Room/SQLite implementation plugs in here. */
interface LocalStore {
    val notes: Flow<List<FieldNote>>
    val preferences: Flow<AppPreferences>
    suspend fun initialize()
    suspend fun addNote(note: FieldNote)
    suspend fun updateNote(note: FieldNote)
    suspend fun deleteNote(id: String)
    suspend fun setPreferences(preferences: AppPreferences)
    suspend fun snapshotJson(): String
}

private val json = Json { encodeDefaults = true; prettyPrint = true }

/** Atomic JSON store in app-private storage. Corrupt state is quarantined, never fatal. */
class FileLocalStore(context: Context) : LocalStore {

    private val file = File(context.filesDir, "farmsteq-state-v1.json")
    private val notesFlow = MutableStateFlow<List<FieldNote>>(emptyList())
    private val prefsFlow = MutableStateFlow(AppPreferences())
    private val mutex = Mutex()

    override val notes: Flow<List<FieldNote>> = notesFlow.asStateFlow()
    override val preferences: Flow<AppPreferences> = prefsFlow.asStateFlow()

    override suspend fun initialize() = mutex.withLock {
        if (!file.exists()) return@withLock
        runCatching { json.decodeFromString<PersistedState>(file.readText()) }
            .onSuccess { state ->
                notesFlow.value = state.notes
                prefsFlow.value = state.preferences
            }
            .onFailure {
                runCatching {
                    Files.move(
                        file.toPath(),
                        File(file.parentFile, "farmsteq-corrupt-${System.currentTimeMillis()}.json").toPath(),
                    )
                }
            }
    }

    override suspend fun addNote(note: FieldNote) = mutex.withLock {
        notesFlow.value = listOf(note) + notesFlow.value
        persistLocked()
    }

    override suspend fun updateNote(note: FieldNote) = mutex.withLock {
        notesFlow.value = notesFlow.value.map { if (it.id == note.id) note else it }
        persistLocked()
    }

    override suspend fun deleteNote(id: String) = mutex.withLock {
        notesFlow.value = notesFlow.value.filterNot { it.id == id }
        persistLocked()
    }

    override suspend fun setPreferences(preferences: AppPreferences) = mutex.withLock {
        prefsFlow.value = preferences
        persistLocked()
    }

    override suspend fun snapshotJson(): String = mutex.withLock {
        json.encodeToString(PersistedState(notesFlow.value, prefsFlow.value))
    }

    private fun persistLocked() {
        val snapshot = json.encodeToString(PersistedState(notesFlow.value, prefsFlow.value))
        val temp = File(file.parentFile, "${file.name}.tmp")
        runCatching {
            temp.writeText(snapshot)
            Files.move(
                temp.toPath(),
                file.toPath(),
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE,
            )
        }.onFailure {
            runCatching { file.writeText(snapshot) }
            runCatching { temp.delete() }
        }
    }
}

fun newNoteId(): String = UUID.randomUUID().toString()
