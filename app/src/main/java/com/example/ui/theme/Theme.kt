package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val MechaDarkColorScheme = darkColorScheme(
  primary = MechaPrimary,
  onPrimary = MechaOnPrimary,
  primaryContainer = MechaPrimaryContainer,
  onPrimaryContainer = MechaOnPrimaryContainer,
  secondary = MechaSecondary,
  onSecondary = MechaOnSecondary,
  secondaryContainer = MechaSecondaryContainer,
  onSecondaryContainer = MechaOnSecondaryContainer,
  tertiary = MechaTertiary,
  onTertiary = MechaOnTertiary,
  tertiaryContainer = MechaTertiaryContainer,
  background = MechaSurface,
  onBackground = MechaOnSurface,
  surface = MechaSurface,
  onSurface = MechaOnSurface,
  surfaceVariant = MechaSurfaceVariant,
  onSurfaceVariant = MechaOnSurfaceVariant,
  surfaceContainerLowest = MechaSurfaceContainerLowest,
  surfaceContainerLow = MechaSurfaceContainerLow,
  surfaceContainer = MechaSurfaceContainer,
  surfaceContainerHigh = MechaSurfaceContainerHigh,
  surfaceContainerHighest = MechaSurfaceContainerHighest,
  outline = MechaOutline,
  outlineVariant = MechaOutlineVariant,
  error = MechaError,
  onError = MechaOnError,
  errorContainer = MechaErrorContainer,
  onErrorContainer = MechaOnErrorContainer
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Enforce our Championship Mecha Apex esports brand identity
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = MechaDarkColorScheme,
    typography = Typography,
    content = content
  )
}
