package com.farmsteq.global.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.farmsteq.global.ui.components.BrandMark
import com.farmsteq.global.ui.components.rememberReducedMotion
import com.farmsteq.global.ui.theme.SeedGreen
import com.farmsteq.global.ui.theme.TealAccent

private data class Page(val icon: ImageVector, val title: String, val body: String)

private val pages = listOf(
    Page(
        Icons.Filled.Eco,
        "Farming, made clearer",
        "Farmerssteq turns what you already do — walking the field, noticing changes — into a simple, useful record.",
    ),
    Page(
        Icons.Filled.EditNote,
        "Your field, your notebook",
        "Record a crop, its stage and how it looks. It works without signal, and your notes stay on your device.",
    ),
    Page(
        Icons.Filled.Lightbulb,
        "One useful next step",
        "Get one calm, practical focus for today. No accounts, no noise, no configuration.",
    ),
)

@Composable
fun OnboardingScreen(onDone: () -> Unit) {
    val reducedMotion = rememberReducedMotion()
    var page by remember { mutableIntStateOf(0) }
    val current = pages[page]

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = onDone) { Text("Skip") }
        }

        if (reducedMotion) {
            OnboardingPage(current)
        } else {
            AnimatedContent(targetState = page, label = "onboarding page") { index ->
                OnboardingPage(pages[index])
            }
        }

        Spacer(Modifier.weight(1f))

        Row(horizontalArrangement = Arrangement.Center) {
            pages.indices.forEach { i ->
                Box(
                    Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(if (i == page) SeedGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                        .height(8.dp)
                        .width(if (i == page) 28.dp else 8.dp),
                )
            }
        }

        Spacer(Modifier.height(28.dp))
        Button(
            onClick = { if (page < pages.lastIndex) page++ else onDone() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(18.dp),
        ) {
            Text(if (page == pages.lastIndex) "Start farming" else "Continue")
        }
        Spacer(Modifier.height(12.dp))
        BrandMark(size = 28.dp)
    }
}

@Composable
private fun OnboardingPage(item: Page) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(48.dp))
        Box(
            Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(SeedGreen, TealAccent))),
            contentAlignment = Alignment.Center,
        ) {
            Icon(item.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(88.dp))
        }
        Spacer(Modifier.height(40.dp))
        Text(item.title, style = MaterialTheme.typography.displaySmall, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Text(
            item.body,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}
