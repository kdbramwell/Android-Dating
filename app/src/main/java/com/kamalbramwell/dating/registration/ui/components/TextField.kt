package com.kamalbramwell.dating.registration.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kamalbramwell.dating.ui.components.DatingText
import com.kamalbramwell.dating.utils.UiText

@Composable
fun Heading(
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