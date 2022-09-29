package org.flepper.currencyconvertor.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.flepper.currencyconvertor.android.presentation.widgets.PayPayTypography

@Composable
fun PayPayAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = colorPrimary,
            primaryVariant = colorPrimaryVariant,
            secondary = colorSecondary,
            //surface = grayDark,
            onSurface = textColor(true)
        )
    } else {
        lightColors(
            primary = colorPrimary,
            primaryVariant = colorPrimaryVariant,
            secondary = colorSecondary,
            onSurface = textColor(false),
        )
    }
    val typography = PayPayTypography

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}