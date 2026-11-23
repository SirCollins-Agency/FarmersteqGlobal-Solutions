package com.farmsteq.global.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farmsteq.global.MainViewModel
import com.farmsteq.global.UiState
import com.farmsteq.global.core.FarmCore
import com.farmsteq.global.ui.components.SectionHeading
import com.farmsteq.global.util.formatNaira
import kotlin.math.roundToInt

private val checklist = listOf(
    "Check soil moisture at root depth",
    "Look for pests on the newest leaves",
    "Record one change you can see",
)

@Composable
fun AdvisoryScreen(state: UiState, viewModel: MainViewModel) {
    var area by remember { mutableFloatStateOf(1f) }
    var eto by remember { mutableFloatStateOf(4f) }
    val checked = remember { mutableStateListOf(false, false, false) }

    val crop = state.notes.firstOrNull()?.crop ?: "Maize"
    val health = state.notes.firstOrNull()?.condition?.toDouble() ?: 70.0
    val factor = FarmCore.cropFactor(crop)
    val litres = FarmCore.waterNeedLitres(area.toDouble(), eto.toDouble(), factor)
    val yieldKg = FarmCore.estimateYieldKg(area.toDouble(), health, factor)

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Text("Advisory", style = MaterialTheme.typography.displaySmall)
        Spacer(Modifier.height(6.dp))
        Text(
            "Practical, locally-computed guidance. No account, no cloud, no guesswork.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(20.dp))
        Card(
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("Water & yield estimator", style = MaterialTheme.typography.titleLarge)
                Text("For $crop · $factor crop factor", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(Modifier.height(16.dp))

                Text("Plot size: ${area.roundToInt().toString()} ha", style = MaterialTheme.typography.titleMedium)
                Slider(value = area, onValueChange = { area = it }, valueRange = 0.25f..10f)
                Text("Evapotranspiration: ${eto.roundToInt()} mm/day", style = MaterialTheme.typography.titleMedium)
                Slider(value = eto, onValueChange = { eto = it }, valueRange = 1f..10f)

                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Metric("Water need", formatNaira(litres).replace("₦", "") + " L")
                    Metric("Indicative yield", "${yieldKg.roundToInt()} kg")
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    "Estimates only · computed on-device by the Farmerssteq core",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }

        Spacer(Modifier.height(26.dp))
        SectionHeading("From your notebook")
        Spacer(Modifier.height(10.dp))
        if (state.notes.isEmpty()) {
            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            ) {
                Text(
                    "Log an observation and tailored advice will appear here immediately.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(18.dp),
                )
            }
        } else {
            state.notes.take(3).forEach { note ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${note.crop} · ${note.stage.label}", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(6.dp))
                        Text(viewModel.adviceFor(note), style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        Spacer(Modifier.height(18.dp))
        SectionHeading("Before you leave the field")
        Spacer(Modifier.height(10.dp))
        checklist.forEachIndexed { index, label ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { checked[index] = !checked[index] }
                    .padding(vertical = 10.dp),
            ) {
                Icon(
                    imageVector = if (checked[index]) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
                    contentDescription = if (checked[index]) "Done" else "Not done",
                    tint = if (checked[index]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                )
                Spacer(Modifier.size(12.dp))
                Text(
                    label,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (checked[index]) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun Metric(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
        Text(value, style = MaterialTheme.typography.titleLarge)
    }
}
