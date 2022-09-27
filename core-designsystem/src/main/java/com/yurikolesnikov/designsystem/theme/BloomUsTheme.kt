package com.yurikolesnikov.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

@Composable
fun BloomUsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            } else {
                if (darkTheme) DarkColorScheme else LightColorScheme
            }
        }
        else -> if (darkTheme) DarkColorScheme else LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@VisibleForTesting
val LightColorScheme = lightColorScheme(
    primary = LightRed,
    onPrimary = White,
    secondary = LightBlue,
    tertiary = Brown,
    background = LightBrown,
    onSurface = GrayHint,
    surfaceVariant = Text

)

@VisibleForTesting
val DarkColorScheme = darkColorScheme(
    primary = LightRed,
    onPrimary = White,
    secondary = LightBlue,
    tertiary = Brown,
    background = LightBrown,
    onSurface = GrayHint,
    surfaceVariant = Text
)