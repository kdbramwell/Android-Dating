package com.kamalbramwell.dating.ui.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.kamalbramwell.dating.utils.UiText

@Composable
fun OnboardingHeading(
    text: UiText?,
    modifier: Modifier = Modifier,
) {
    text?.let {
        DatingText(
            text = text,
            modifier = modifier,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 72.sp,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AppBarTitle(text: UiText, modifier: Modifier = Modifier) {
    DatingText(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}
@Composable
fun DatingText(
    text: UiText,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = text.asString(),
        fontSize = fontSize,
        lineHeight = lineHeight,
        fontWeight = fontWeight,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        modifier = modifier,
        style = style,
        textAlign = textAlign,
    )
}