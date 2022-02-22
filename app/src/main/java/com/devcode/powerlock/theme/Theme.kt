package com.devcode.powerlock.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette =  darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background =blackBackground,
    onPrimary =whiteTextColor,

    surface = Color(0xFF161616),
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    secondaryVariant = placeHolderTextColor

)


private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    background =whiteBackground,
    onPrimary =blackTextColor,

    surface = Color(0xFFC9C6C6),
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,


)

@Composable
fun SecurePhoneAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette

    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}