package com.kamalbramwell.dating.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.ui.theme.defaultShadowElevation
import com.kamalbramwell.dating.utils.UiText

@Composable
fun MaxWidthButton(
    label: UiText,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().zIndex(1f),
        enabled = enabled,
    ) {
        Text(text = label.asString())
    }
}

@Composable
fun MaxWidthBorderlessButton(
    label: UiText,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().zIndex(1f),
        enabled = enabled,
    ) {
        Text(text = label.asString())
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(64.dp)
            .zIndex(1f)
            .shadow(
                elevation = defaultShadowElevation,
                shape = CircleShape
            ),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outline),
        enabled =  enabled
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = UiText.StringResource(R.string.back).asString()
        )
    }
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(64.dp)
            .zIndex(1f)
            .shadow(
                elevation = defaultShadowElevation,
                shape = CircleShape
            ),
        shape = CircleShape,
        enabled =  enabled,
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = UiText.StringResource(R.string.next).asString()
        )
    }
}