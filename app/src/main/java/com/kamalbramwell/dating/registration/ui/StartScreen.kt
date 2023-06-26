package com.kamalbramwell.dating.registration.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.registration.data.StockImageDataSource
import com.kamalbramwell.dating.ui.components.Heading
import com.kamalbramwell.dating.ui.components.MaxWidthBorderlessButton
import com.kamalbramwell.dating.ui.components.MaxWidthButton
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.utils.UiText

const val RegistrationOptionsTestTag  = "StartScreen"

@Composable
fun StartScreen(
    onEmailRegistrationClicked: () -> Unit = {},
    onPhoneRegistrationClicked: () -> Unit = {},
    onAlreadyRegisteredClicked: () -> Unit = {}
) {
    val resourceId = remember { StockImageDataSource.random() }
    Box(Modifier.semantics { testTag = RegistrationOptionsTestTag }) {
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(Modifier.weight(1f).padding(defaultContentPadding))

            Spacer(Modifier.size(16.dp))

            Column(
                Modifier.background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                    )
                ).padding(defaultContentPadding),
            ) {
                EmailRegistrationButton(onEmailRegistrationClicked)

                Spacer(Modifier.size(16.dp))

                PhoneRegistrationButton(onPhoneRegistrationClicked)

                Spacer(Modifier.size(16.dp))

                AlreadyRegisteredButton(onAlreadyRegisteredClicked)
            }
        }
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier
) {
    Heading(
        text = UiText.StringResource(R.string.app_subtitle),
        modifier = modifier
    )
}

@Composable
private fun EmailRegistrationButton(onClick: () -> Unit = {}) {
    MaxWidthButton(
        label = UiText.StringResource(R.string.registration_email_registration_cta),
        onClick = onClick
    )
}

@Composable
private fun PhoneRegistrationButton(onClick: () -> Unit = {}) {
    MaxWidthButton(
        label = UiText.StringResource(R.string.registration_phone_registration_cta),
        onClick = onClick
    )
}

@Composable
private fun AlreadyRegisteredButton(onClick: () -> Unit = {}) {
    MaxWidthBorderlessButton(
        label = UiText.StringResource(R.string.registration_already_registered_cta),
        onClick = onClick,
        color = Color.White
    )
}

@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    DatingTheme {
        StartScreen()
    }
}

@Preview
@Composable
private fun RegistrationScreenDarkPreview() {
    DatingTheme(darkTheme = true) {
        StartScreen()
    }
}