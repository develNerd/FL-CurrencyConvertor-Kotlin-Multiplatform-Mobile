package org.flepper.currencyconvertor.android.theme

import androidx.compose.ui.graphics.Color

val colorPrimary = Color(0xFFDD3535)
val colorPrimaryVariant = Color(0xFF880D0D)
val colorSecondary = Color(0xFF03DAC5)
fun textColor(isDark: Boolean) = if(isDark) textDark else  textLight
val colorAccent = Color(0xFF008095)
val colorWhite = Color(0xFFFFFFFF)
val textDark = Color(0xFFF0ECEC)
val textLight = Color(0xC8000000)

val lightGreenTrans64 = Color(0xFFECFBD9)
val grayDark = Color(0xFFB6B2B2)
val systemGrayText = Color(0xFF9FA6AD)
val systemGrayDark = Color(0xFF626E77)
val transWhite = Color(0x4FFFFFFF)



val gray = Color(0xFF79838B)
val textGray = Color(0xFF626E77)
val textWhite = Color(0xFFD1D7DB)
val transGray = Color(0x41626E77)
val textColorDark = Color(0xC8000000)
val onBackGroundLight = Color(0xFF413F3F)
fun color(isDark:Boolean) = if (isDark) colorPrimary else colorPrimaryVariant