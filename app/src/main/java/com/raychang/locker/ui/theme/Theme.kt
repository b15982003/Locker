package com.raychang.locker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = AppStyle.Colors.LightScheme.primary,
    onPrimary = AppStyle.Colors.LightScheme.onPrimary,
    primaryContainer = AppStyle.Colors.LightScheme.primaryContainer,
    onPrimaryContainer = AppStyle.Colors.LightScheme.onPrimaryContainer,

    secondary = AppStyle.Colors.LightScheme.secondary,
    onSecondary = AppStyle.Colors.LightScheme.onSecondary,
    secondaryContainer = AppStyle.Colors.LightScheme.secondaryContainer,
    onSecondaryContainer = AppStyle.Colors.LightScheme.onSecondaryContainer,

    tertiary = AppStyle.Colors.LightScheme.tertiary,
    onTertiary = AppStyle.Colors.LightScheme.onTertiary,
    tertiaryContainer = AppStyle.Colors.LightScheme.tertiaryContainer,
    onTertiaryContainer = AppStyle.Colors.LightScheme.onTertiaryContainer,

    error = AppStyle.Colors.LightScheme.error,
    onError = AppStyle.Colors.LightScheme.onError,
    errorContainer = AppStyle.Colors.LightScheme.errorContainer,
    onErrorContainer = AppStyle.Colors.LightScheme.onErrorContainer,

    background = AppStyle.Colors.LightScheme.background,
    onBackground = AppStyle.Colors.LightScheme.onBackground,
    surface = AppStyle.Colors.LightScheme.surface,
    onSurface = AppStyle.Colors.LightScheme.onSurface,
    surfaceVariant = AppStyle.Colors.LightScheme.surfaceVariant,
    onSurfaceVariant = AppStyle.Colors.LightScheme.onSurfaceVariant,

    outline = AppStyle.Colors.LightScheme.outline,
    outlineVariant = AppStyle.Colors.LightScheme.outlineVariant,

    surfaceContainerLowest = AppStyle.Colors.LightScheme.surfaceContainerLowest,
    surfaceContainerLow = AppStyle.Colors.LightScheme.surfaceContainerLow,
    surfaceContainer = AppStyle.Colors.LightScheme.surfaceContainer,
    surfaceContainerHigh = AppStyle.Colors.LightScheme.surfaceContainerHigh,
    surfaceContainerHighest = AppStyle.Colors.LightScheme.surfaceContainerHighest
)

private val DarkColorScheme = darkColorScheme(
    primary = AppStyle.Colors.DarkScheme.primary,
    onPrimary = AppStyle.Colors.DarkScheme.onPrimary,
    primaryContainer = AppStyle.Colors.DarkScheme.primaryContainer,
    onPrimaryContainer = AppStyle.Colors.DarkScheme.onPrimaryContainer,

    secondary = AppStyle.Colors.DarkScheme.secondary,
    onSecondary = AppStyle.Colors.DarkScheme.onSecondary,
    secondaryContainer = AppStyle.Colors.DarkScheme.secondaryContainer,
    onSecondaryContainer = AppStyle.Colors.DarkScheme.onSecondaryContainer,

    tertiary = AppStyle.Colors.DarkScheme.tertiary,
    onTertiary = AppStyle.Colors.DarkScheme.onTertiary,
    tertiaryContainer = AppStyle.Colors.DarkScheme.tertiaryContainer,
    onTertiaryContainer = AppStyle.Colors.DarkScheme.onTertiaryContainer,

    error = AppStyle.Colors.DarkScheme.error,
    onError = AppStyle.Colors.DarkScheme.onError,
    errorContainer = AppStyle.Colors.DarkScheme.errorContainer,
    onErrorContainer = AppStyle.Colors.DarkScheme.onErrorContainer,

    background = AppStyle.Colors.DarkScheme.background,
    onBackground = AppStyle.Colors.DarkScheme.onBackground,
    surface = AppStyle.Colors.DarkScheme.surface,
    onSurface = AppStyle.Colors.DarkScheme.onSurface,
    surfaceVariant = AppStyle.Colors.DarkScheme.surfaceVariant,
    onSurfaceVariant = AppStyle.Colors.DarkScheme.onSurfaceVariant,

    outline = AppStyle.Colors.DarkScheme.outline,
    outlineVariant = AppStyle.Colors.DarkScheme.outlineVariant,

    surfaceContainerLowest = AppStyle.Colors.DarkScheme.surfaceContainerLowest,
    surfaceContainerLow = AppStyle.Colors.DarkScheme.surfaceContainerLow,
    surfaceContainer = AppStyle.Colors.DarkScheme.surfaceContainer,
    surfaceContainerHigh = AppStyle.Colors.DarkScheme.surfaceContainerHigh,
    surfaceContainerHighest = AppStyle.Colors.DarkScheme.surfaceContainerHighest
)

@Composable
fun LockerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
