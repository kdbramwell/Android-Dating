package com.kamalbramwell.dating.registration.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.registration.data.StockImageDataSource
import com.kamalbramwell.dating.ui.components.MaxWidthBorderlessButton
import com.kamalbramwell.dating.ui.components.MaxWidthButton
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.utils.UiText

@Composable
fun RegistrationOptionsScreen(
    onEmailRegistrationClicked: () -> Unit = {},
    onPhoneRegistrationClicked: () -> Unit = {},
    onAlreadyRegisteredClicked: () -> Unit = {}
) {
    Box {
        Image(
            painter = painterResource(id = StockImageDataSource.random()),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailRegistrationButton(onEmailRegistrationClicked)
            PhoneRegistrationButton(onPhoneRegistrationClicked)
            AlreadyRegisteredButton(onAlreadyRegisteredClicked)
        }
    }
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
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    DatingTheme {
        RegistrationOptionsScreen()
    }
}

@Preview
@Composable
private fun RegistrationScreenDarkPreview() {
    DatingTheme(darkTheme = true) {
        RegistrationOptionsScreen()
    }
}