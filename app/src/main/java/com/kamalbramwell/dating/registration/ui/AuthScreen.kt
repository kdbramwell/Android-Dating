package com.kamalbramwell.dating.registration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.ui.components.InputField
import com.kamalbramwell.dating.ui.components.MaxWidthBorderlessButton
import com.kamalbramwell.dating.ui.components.MaxWidthButton
import com.kamalbramwell.dating.utils.UiText
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamalbramwell.dating.toast.ui.ErrorToast
import com.kamalbramwell.dating.ui.components.DatingText
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding

const val AuthTestTag = "AuthScreen"

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = viewModel(),
    onNavigateNext: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AuthScreen(
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
fun AuthScreen(
    uiState: RegistrationState,
    onEmailOrPhoneInput: (TextFieldValue) -> Unit = {},
    onPasswordInput: (TextFieldValue) -> Unit = {},
    onNextClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {}
) {
    val nextButtonEnabled by remember(uiState) {
        derivedStateOf {
            uiState.run {
                emailOrPhoneError == null && passwordError == null && !isLoading && !registrationSuccessful
            }
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(defaultContentPadding)
            .semantics { testTag = AuthTestTag },
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            Modifier.weight(1F),
            verticalArrangement = Arrangement.Top
        ) {
            Heading(uiState.heading)
            EmailOrPhoneInput(
                textFieldValue = uiState.emailOrPhone,
                onTextChanged = onEmailOrPhoneInput,
                error = uiState.emailOrPhoneError,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PasswordInput(
                textFieldValue = uiState.password,
                onTextChanged = onPasswordInput,
                error = uiState.passwordError,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Column(
            Modifier.weight(1F),
            verticalArrangement = Arrangement.Bottom
        ) {
            TaskError(uiState.taskError)
            ContinueButton(
                onClick = onNextClicked,
                enabled = nextButtonEnabled,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            CancelButton(
                onClick = onCancelClicked,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun Heading(
    heading: UiText?,
    modifier: Modifier = Modifier,
) {
    heading?.let {
        DatingText(
            heading,
            modifier,
            fontSize = 72.sp,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
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
        placeholder = placeholder,
        label = placeholder
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
        placeholder = placeholder,
        label = placeholder
    )
}


@Composable
private fun TaskError(
    error: UiText?,
    modifier: Modifier = Modifier
) {
    error?.let {
        ErrorToast(
            title = error.asString(),
            modifier = modifier
        )
    }
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
private fun CreateAccountScreenPreview() {
    DatingTheme {
        AuthScreen(
            RegistrationState(
                heading = UiText.DynamicString("Welcome back."),
                taskError = UiText.DynamicString("Check internet connection")
            )
        )
    }
}

@Preview
@Composable
private fun CreateAccountScreenPreviewDarkPreview() {
    DatingTheme(darkTheme = true) {
        AuthScreen(
            RegistrationState(
                heading = UiText.DynamicString("Welcome back."),
                taskError = UiText.DynamicString("Check internet connection")
            )
        )
    }
}