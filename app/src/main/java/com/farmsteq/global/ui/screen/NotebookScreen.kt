@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.farmsteq.global.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farmsteq.global.MainViewModel
import com.farmsteq.global.UiState
import com.farmsteq.global.data.CropStage
import com.farmsteq.global.ui.components.NoteCard
import com.farmsteq.global.ui.components.SectionHeading
import kotlin.math.roundToInt

@Composable
fun NotebookScreen(state: UiState, viewModel: MainViewModel) {
    var showSheet by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showSheet = true }) {
                androidx.compose.material3.Icon(Icons.Filled.Add, contentDescription = "Add field note")
            }
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            Text("Field Notebook", style = MaterialTheme.typography.displaySmall)
            Spacer(Modifier.height(4.dp))
            Text(
                "${state.notes.size} note${if (state.notes.size == 1) "" else "s"} stored on this device",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(20.dp))
            SectionHeading("Your observations")
            Spacer(Modifier.height(12.dp))

            if (state.notes.isEmpty()) {
                Card(
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text("Nothing logged yet", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Tap + to record a crop, its stage and what you noticed. It works with no signal.",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(state.notes, key = { it.id }) { note ->
                        NoteCard(note = note, onDelete = { viewModel.deleteNote(note.id) })
                    }
                    item { Spacer(Modifier.height(80.dp)) }
                }
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(onDismissRequest = { showSheet = false }) {
            AddNoteSheet(
                onSave = { crop, stage, condition, plotHa, note ->
                    viewModel.addNote(crop, stage, condition, plotHa, note)
                    showSheet = false
                },
            )
        }
    }
}

@Composable
private fun AddNoteSheet(onSave: (String, CropStage, Int, Double, String) -> Unit) {
    var crop by remember { mutableStateOf("Maize") }
    var stage by remember { mutableStateOf(CropStage.SEEDLING) }
    var condition by remember { mutableIntStateOf(75) }
    var plotHa by remember { mutableDoubleStateOf(1.0) }
    var note by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
    ) {
        Text("New field observation", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = crop,
            onValueChange = { crop = it },
            label = { Text("Crop") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("Maize", "Cassava", "Yam", "Rice", "Sorghum", "Groundnut").forEach { suggestion ->
                FilterChip(
                    selected = crop == suggestion,
                    onClick = { crop = suggestion },
                    label = { Text(suggestion, style = MaterialTheme.typography.labelSmall) },
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Growth stage", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CropStage.entries.forEach { option ->
                FilterChip(
                    selected = stage == option,
                    onClick = { stage = option },
                    label = { Text(option.label, style = MaterialTheme.typography.labelSmall) },
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Crop condition: $condition%", style = MaterialTheme.typography.titleMedium)
        Slider(
            value = condition.toFloat(),
            onValueChange = { condition = it.roundToInt() },
            valueRange = 0f..100f,
            steps = 9,
        )

        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = plotHa.toString(),
            onValueChange = { value -> value.toDoubleOrNull()?.let { plotHa = it } },
            label = { Text("Plot size (hectares)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("What did you notice?") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { onSave(crop, stage, condition, plotHa, note) },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text("Save observation")
        }
        Spacer(Modifier.height(20.dp))
    }
}
