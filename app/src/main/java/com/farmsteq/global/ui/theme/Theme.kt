package com.farmsteq.global.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = SeedGreen,
    onPrimary = Color.White,
    primaryContainer = SeedGreenLight,
    onPrimaryContainer = SeedGreenDark,
    secondary = TealAccent,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFC6F2EB),
    onSecondaryContainer = Color(0xFF073B36),
    tertiary = GoldAccent,
    background = SurfaceLight,
    onBackground = SeedGreenDark,
    surface = CardLight,
    onSurface = SeedGreenDark,
    onSurfaceVariant = MutedTextLight,
    outline = Color(0xFFB8CABE),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8FE3B4),
    onPrimary = Color(0xFF06301F),
    primaryContainer = Color(0xFF155C3E),
    onPrimaryContainer = Color(0xFFD6FBE4),
    secondary = Color(0xFF7ADBD0),
    onSecondary = Color(0xFF06302B),
    secondaryContainer = Color(0xFF11534C),
    onSecondaryContainer = Color(0xFFD3F7F2),
    tertiary = Color(0xFFF2CE7A),
    background = SurfaceDark,
    onBackground = Color(0xFFE4F2E9),
    surface = CardDark,
    onSurface = Color(0xFFE4F2E9),
    onSurfaceVariant = MutedTextDark,
    outline = Color(0xFF476153),
)

@Composable
fun FarmerssteqTheme(
    theme: String = "system",
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val dark = when (theme) {
        "dark" -> true
        "light" -> false
        else -> isSystemInDarkTheme()
    }
    val colors = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (dark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else if (dark) DarkColors else LightColors

    MaterialTheme(colorScheme = colors, typography = AppTypography, content = content)
}
