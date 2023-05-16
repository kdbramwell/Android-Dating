package com.kamalbramwell.dating.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MaxWidthButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text = label)
    }
}