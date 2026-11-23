package com.farmsteq.global.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.farmsteq.global.MainViewModel
import com.farmsteq.global.UiState
import com.farmsteq.global.ui.components.BrandMark
import com.farmsteq.global.ui.components.FocusCard
import com.farmsteq.global.ui.components.NoteCard
import com.farmsteq.global.ui.components.SectionHeading
import com.farmsteq.global.ui.components.rememberReducedMotion
import com.farmsteq.global.ui.theme.TealAccent
import com.farmsteq.global.util.greetingForHour
import java.util.Calendar

@Composable
fun HomeScreen(
    state: UiState,
    viewModel: MainViewModel,
    onOpenNotebook: () -> Unit,
    onOpenAdvisory: () -> Unit,
    onOpenSolutions: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val reducedMotion = rememberReducedMotion()
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            BrandMark(modifier = Modifier.weight(1f))
            IconButton(onClick = onOpenSettings) {
                Icon(Icons.Filled.Settings, contentDescription = "Open settings")
            }
        }

        Spacer(Modifier.height(28.dp))
        Text(
            "${greetingForHour(hour)}, farmer.",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            "A small step in the field today, a better season ahead.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(20.dp))
        FocusCard(focus = state.focus, reducedMotion = reducedMotion, onAction = onOpenNotebook)

        Spacer(Modifier.height(28.dp))
        SectionHeading("Quick actions")
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            QuickAction("Log note", Icons.Filled.EditNote, Modifier.weight(1f), onOpenNotebook)
            QuickAction("Advisory", Icons.Filled.Insights, Modifier.weight(1f), onOpenAdvisory)
            QuickAction("Solutions", Icons.Filled.Layers, Modifier.weight(1f), onOpenSolutions)
        }

        Spacer(Modifier.height(28.dp))
        SectionHeading("Recent observations", "See all", onOpenNotebook)
        Spacer(Modifier.height(12.dp))
        if (state.notes.isEmpty()) {
            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            ) {
                Text(
                    "No notes yet. Your first observation takes under a minute and works offline.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(20.dp),
                )
            }
        } else {
            state.notes.take(2).forEach { note ->
                NoteCard(note = note, onDelete = { viewModel.deleteNote(note.id) })
                Spacer(Modifier.height(10.dp))
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun QuickAction(label: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .height(96.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            Box(
                Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(TealAccent.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = TealAccent, modifier = Modifier.size(18.dp))
            }
            Text(label, style = MaterialTheme.typography.labelLarge)
        }
    }
}
