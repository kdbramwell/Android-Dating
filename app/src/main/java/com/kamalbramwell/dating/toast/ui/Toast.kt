package com.kamalbramwell.dating.toast.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.kamalbramwell.dating.ui.theme.DatingTheme

private val defaultIconSize = 24.dp

private val defaultNoticeIcon = @Composable {
    Icon(
        imageVector = Icons.Outlined.Info,
        contentDescription = null,
        modifier = Modifier.size(defaultIconSize),
        tint = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun NoticeToast(
    title: String,
    modifier: Modifier = Modifier,
    description: String = "",
    leadingIcon: @Composable () -> Unit = defaultNoticeIcon,
    trailingIcon: @Composable () -> Unit = {}
) {
    DatingToast(
        backgroundColor = MaterialTheme.colorScheme.outline,
        title = title,
        description = description,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}

private val defaultConfirmIcon = @Composable {
    Icon(
        imageVector = Icons.Outlined.Check,
        contentDescription = null,
        modifier = Modifier.size(defaultIconSize),
        tint = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun ConfirmationToast(
    title: String,
    modifier: Modifier = Modifier,
    description: String = "",
    leadingIcon: @Composable () -> Unit = defaultConfirmIcon,
    trailingIcon: @Composable () -> Unit = {}
) {
    DatingToast(
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        title = title,
        description = description,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}

private val defaultErrorIcon = @Composable {
    Icon(
        imageVector = Icons.Outlined.Error,
        contentDescription = null,
        modifier = Modifier.size(defaultIconSize),
        tint = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun ErrorToast(
    title: String,
    modifier: Modifier = Modifier,
    description: String = "",
    leadingIcon: @Composable () -> Unit = defaultErrorIcon,
    trailingIcon: @Composable () -> Unit = {}
) {
    DatingToast(
        backgroundColor = MaterialTheme.colorScheme.error,
        title = title,
        description = description,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}

@Composable
private fun DatingToast(
    backgroundColor: Color,
    title: String,
    modifier: Modifier = Modifier,
    description: String = "",
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingIcon()

        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            if (description.isNotBlank()) {
                Text(
                    text = description,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start
                )
            }
        }

        trailingIcon()
    }
}

@Preview
@Composable
private fun NoticeToastDarkPreview() {
    DatingTheme(darkTheme = true) {
        NoticeToast(
            title = LoremIpsum(
                (5..20).random()
            ).values.joinToString(),
            description = LoremIpsum(
                (5..20).random()
            ).values.joinToString()
        )
    }
}

@Preview
@Composable
private fun ConfirmationToastPreview() {
    DatingTheme(darkTheme = true) {
        ConfirmationToast(
            title = LoremIpsum(
                (5..20).random()
            ).values.joinToString(),
            description = LoremIpsum(
                (5..20).random()
            ).values.joinToString()
        )
    }
}

@Preview
@Composable
private fun ErrorToastPreview() {
    DatingTheme(darkTheme = true) {
        ErrorToast(
            title = LoremIpsum(
                (5..20).random()
            ).values.joinToString(),
            description = LoremIpsum(
                (5..20).random()
            ).values.joinToString()
        )
    }
}