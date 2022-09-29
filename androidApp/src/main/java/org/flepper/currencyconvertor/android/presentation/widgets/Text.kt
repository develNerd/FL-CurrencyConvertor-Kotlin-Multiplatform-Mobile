package org.flepper.currencyconvertor.android.presentation.widgets

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


@Composable
fun LargeBoldText(text:String?, modifier: Modifier = Modifier, color: Color = MaterialTheme.colors.onSurface,fontSize:TextUnit = large_bold_text_size,fontStyle: FontStyle? = null, lineHeight: TextUnit = 50.sp){
    Text(modifier = modifier,text = text ?: "", fontFamily = LocalFontThemes.current.fontFamily , color = color,fontSize = fontSize,fontStyle = fontStyle, fontWeight =  FontWeight.Bold)
}

@Composable
fun MediumTextBold(text:String?, modifier: Modifier = Modifier, textSize: TextUnit = medium_bold_text_size, color: Color = MaterialTheme.colors.onSurface, textAlign: TextAlign = TextAlign.Start, lineHeight: TextUnit = TextUnit.Unspecified,fontWeight: FontWeight = FontWeight.Medium,fontStyle: FontStyle? = null,maxLines:Int = Int.MAX_VALUE){
    Text(text ?: "", modifier = modifier , textAlign = textAlign,fontFamily = LocalFontThemes.current.fontFamily , color = color,fontSize = textSize, fontWeight = fontWeight, lineHeight = lineHeight, fontStyle = fontStyle, maxLines = maxLines, overflow = TextOverflow.Ellipsis)
}

@Composable
fun RegularText(text:String?, modifier: Modifier = Modifier, color: Color = MaterialTheme.colors.onSurface, size: TextUnit = medium_bold_text_size, textAlign: TextAlign = TextAlign.Start){
    Text(text ?: "", modifier = modifier ,fontFamily = LocalFontThemes.current.fontFamily , color = color,fontSize = size, fontWeight =  FontWeight.Normal, textAlign = textAlign)
}