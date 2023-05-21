package com.kamalbramwell.dating.registration.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.ui.components.InputField
import com.kamalbramwell.dating.ui.components.MaxWidthBorderlessButton
import com.kamalbramwell.dating.ui.components.MaxWidthButton
import com.kamalbramwell.dating.utils.UiText
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamalbramwell.dating.ui.theme.DatingTheme

@Composable
fun AccountRegistrationScreen(
    viewModel: RegistrationViewModel = viewModel(),
    onNavigateNext: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AccountRegistrationLayout(
        uiState = uiState,
        onEmailOrPhoneInput = viewModel::onEmailOrPhoneInput,
        onPasswordInput = viewModel::onPasswordInput,
        onNextClicked = viewModel::onNextClicked,
        onCancelClicked = onCancelClicked
    )

    if (uiState.registrationSuccessful) {
        onNavigateNext()
    }
}

@Composable
private fun AccountRegistrationLayout(
    uiState: RegistrationState,
    onEmailOrPhoneInput: (TextFieldValue) -> Unit = {},
    onPasswordInput: (TextFieldValue) -> Unit = {},
    onNextClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {}
) {
    Column {
        EmailOrPhoneInput(
            textFieldValue = uiState.emailOrPhone,
            onTextChanged = onEmailOrPhoneInput,
            error = uiState.emailOrPhoneError
        )
        PasswordInput(
            textFieldValue = uiState.password,
            onTextChanged = onPasswordInput,
            error = uiState.passwordError
        )
        ContinueButton(
            onClick = onNextClicked,
            enabled = uiState.nextButtonEnabled
        )
        CancelButton(onClick = onCancelClicked)
    }
}

@Composable
private fun EmailOrPhoneInput(
    textFieldValue: TextFieldValue,
    modifier: Modifier = Modifier,
    onTextChanged: (TextFieldValue) -> Unit = {},
    onTextFieldFocused: (Boolean) -> Unit = {},
    error: UiText? = null,
) {
    val placeholder = remember { UiText.StringResource(R.string.registration_email_or_phone_label) }
    InputField(
        textFieldValue = textFieldValue,
        modifier = modifier,
        onTextChanged = onTextChanged,
        onTextFieldFocused = onTextFieldFocused,
        error = error,
        placeholder = placeholder
    )
}

@Composable
private fun PasswordInput(
    textFieldValue: TextFieldValue,
    modifier: Modifier = Modifier,
    onTextChanged: (TextFieldValue) -> Unit = {},
    onTextFieldFocused: (Boolean) -> Unit = {},
    error: UiText? = null,
) {
    val placeholder = remember { UiText.StringResource(R.string.registration_password_label) }
    InputField(
        textFieldValue = textFieldValue,
        modifier = modifier,
        onTextChanged = onTextChanged,
        onTextFieldFocused = onTextFieldFocused,
        error = error,
        placeholder = placeholder
    )
}

@Composable
private fun ContinueButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit = {},
) {
    MaxWidthButton(
        label = UiText.StringResource(R.string.next),
        modifier = modifier,
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
private fun CancelButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    MaxWidthBorderlessButton(
        label = UiText.StringResource(R.string.back),
        modifier = modifier,
        onClick = onClick
    )
}

@Preview
@Composable
private fun PhoneRegistrationPreview() {
    DatingTheme {
        AccountRegistrationLayout(RegistrationState())
    }
}

@Preview(showBackground = true)
@Composable
private fun PhoneRegistrationDarkPreview() {
    DatingTheme(darkTheme = true) {
        AccountRegistrationLayout(RegistrationState())
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountRegistrationScreenPreview() {
    DatingTheme {
        AccountRegistrationLayout(RegistrationState())
    }
}

@Preview
@Composable
private fun AccountRegistrationScreenDarkPreview() {
    DatingTheme(darkTheme = true) {
        AccountRegistrationLayout(RegistrationState())
    }
}