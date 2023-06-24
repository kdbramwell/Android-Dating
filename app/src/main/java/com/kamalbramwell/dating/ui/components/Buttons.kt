package com.kamalbramwell.dating.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.magenta
import com.kamalbramwell.dating.ui.theme.orange
import com.kamalbramwell.dating.utils.UiText

@Composable
fun rememberBrandGradient(): Brush = remember {
    Brush.horizontalGradient(listOf(orange, magenta))
}

@Composable
fun MaxWidthButton(
    label: UiText,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val brandGradient = rememberBrandGradient()
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .background(brandGradient, MaterialTheme.shapes.large),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        enabled = enabled,
    ) {
        DatingText(text = label)
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
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
    ) {
        DatingText(text = label)
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
        modifier = modifier.size(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outline),
        enabled =  enabled
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = UiText.StringResource(R.string.back).asString()
        )
    }
}

@JvmOverloads
@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit = {},
) {
    val brandGradient = rememberBrandGradient()

    Button(
        onClick = onClick,
        modifier = modifier
            .size(64.dp)
            .background(brandGradient, CircleShape),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        enabled =  enabled,
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = UiText.StringResource(R.string.next).asString()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MaxWidthButtonPreview() {
    DatingTheme {
        MaxWidthButton(
            label = UiText.DynamicString("Continue"),
            enabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MaxWidthBorderlessButtonPreview() {
    DatingTheme {
        MaxWidthBorderlessButton(
            label = UiText.DynamicString("Cancel"),
            enabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BackButtonEnabledPreview() {
    DatingTheme {
        BackButton(enabled = true)
    }
}


@Preview(showBackground = true)
@Composable
private fun BackButtonDisabledPreview() {
    DatingTheme {
        BackButton(enabled = false)
    }
}

@Preview(showBackground = true)
@Composable
private fun NextButtonEnabledPreview() {
    DatingTheme {
        NextButton(enabled = true)
    }
}

@Preview(showBackground = true)
@Composable
private fun NextButtonDisabledPreview() {
    DatingTheme {
        NextButton(enabled = false)
    }
}