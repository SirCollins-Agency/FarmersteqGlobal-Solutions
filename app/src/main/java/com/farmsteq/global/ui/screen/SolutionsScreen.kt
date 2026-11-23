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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingFlat
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.farmsteq.global.data.MARKET_SAMPLE_LABEL
import com.farmsteq.global.data.SolutionIcon
import com.farmsteq.global.data.SolutionStatus
import com.farmsteq.global.data.sampleMarket
import com.farmsteq.global.data.solutionTracks
import com.farmsteq.global.ui.components.SampleBadge
import com.farmsteq.global.ui.components.SectionHeading
import com.farmsteq.global.util.formatNaira

/**
 * The Solutions module: a structured, honest catalogue of Farmerssteq capabilities,
 * with the bundled market-signal board as one track rather than a standalone tab.
 */
@Composable
fun SolutionsScreen(
    onOpenNotebook: () -> Unit = {},
    onOpenAdvisory: () -> Unit = {},
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text("Solutions", style = MaterialTheme.typography.displaySmall)
            Spacer(Modifier.height(6.dp))
            Text(
                "Everything Farmerssteq can do for your farm — clearly structured, honestly labelled.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(10.dp))
        }

        item { SectionHeading("The Farmerssteq toolkit") }

        items(solutionTracks, key = { it.id }) { track ->
            SolutionTrackCard(
                track = track,
                onClick = when (track.id) {
                    "notebook" -> onOpenNotebook
                    "advisory" -> onOpenAdvisory
                    else -> null
                },
            )
        }

        item {
            Spacer(Modifier.height(14.dp))
            SectionHeading("Market signals")
            Spacer(Modifier.height(4.dp))
            SampleBadge(MARKET_SAMPLE_LABEL)
            Spacer(Modifier.height(8.dp))
            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            ) {
                Text(
                    "Indicative sample prices bundled with v0.0.1 — not live quotes. Record what buyers actually offer you in the Field Notebook.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }

        items(sampleMarket, key = { "price-${it.commodity}" }) { item ->
            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            item.commodity.first().toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                    Spacer(Modifier.size(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text(item.commodity, style = MaterialTheme.typography.titleMedium)
                        Text(item.note, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(formatNaira(item.pricePerKg), style = MaterialTheme.typography.titleMedium)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val (icon, tint) = when {
                                item.changePercent > 0 -> Icons.Filled.TrendingUp to Color(0xFF1B8A4B)
                                item.changePercent < 0 -> Icons.Filled.TrendingDown to Color(0xFFB23B3B)
                                else -> Icons.Filled.TrendingFlat to Color.Gray
                            }
                            Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
                            Text(
                                "${if (item.changePercent > 0) "+" else ""}${item.changePercent}%",
                                style = MaterialTheme.typography.labelSmall,
                                color = tint,
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Composable
private fun SolutionTrackCard(
    track: com.farmsteq.global.data.SolutionTrack,
    onClick: (() -> Unit)?,
) {
    val icon: ImageVector = when (track.icon) {
        SolutionIcon.NOTEBOOK -> Icons.Filled.EditNote
        SolutionIcon.ADVISORY -> Icons.Filled.Insights
        SolutionIcon.MARKET -> Icons.Filled.Storefront
        SolutionIcon.BUSINESS -> Icons.Filled.Lightbulb
        SolutionIcon.SUSTAINABILITY -> Icons.Filled.Eco
    }
    val container = if (track.status == SolutionStatus.AVAILABLE) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.Top) {
            Box(
                Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(container),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(track.title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        track.status.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (track.status == SolutionStatus.AVAILABLE) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(track.summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
