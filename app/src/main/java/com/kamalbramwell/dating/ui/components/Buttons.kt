package com.kamalbramwell.dating.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kamalbramwell.dating.utils.UiText

@Composable
fun MaxWidthButton(
    label: UiText,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    textColor: Color = MaterialTheme.colorScheme.onSecondary,
    onClick: () -> Unit = {},
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            containerColor = backgroundColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.35f),
            contentColor = textColor
        )
    ) {
        Text(text = label.asString())
    }
}

@Composable
fun MaxWidthBorderlessButton(
    label: UiText,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {},
) {
    MaxWidthButton(
        label = label,
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        backgroundColor = Color.Transparent,
        textColor = textColor
    )
}