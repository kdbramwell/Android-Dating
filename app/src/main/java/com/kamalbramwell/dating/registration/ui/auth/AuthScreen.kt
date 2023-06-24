package com.kamalbramwell.dating.registration.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.registration.ui.components.Heading
import com.kamalbramwell.dating.toast.ui.ErrorToast
import com.kamalbramwell.dating.ui.components.BackButton
import com.kamalbramwell.dating.ui.components.InputField
import com.kamalbramwell.dating.ui.components.NextButton
import com.kamalbramwell.dating.ui.components.PasswordField
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.utils.UiText

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

    LaunchedEffect(uiState.registrationSuccessful) {
        if (uiState.registrationSuccessful) {
            onNavigateNext()
        }
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

            Spacer(Modifier.height(8.dp))

            TaskError(uiState.taskError)
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                CancelButton(onClick = onCancelClicked,)

                Spacer(Modifier.weight(1F))

                ContinueButton(
                    onClick = onNextClicked,
                    enabled = nextButtonEnabled,
                )
            }
        }
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
        modifier = modifier.defaultMinSize(minHeight = 64.dp),
        onTextChanged = onTextChanged,
        onTextFieldFocused = onTextFieldFocused,
        error = error,
        placeholder = placeholder,
        label = placeholder,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
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
    PasswordField(
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
    NextButton(
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
    BackButton(
        modifier = modifier,
        enabled = true,
        onClick = onClick
    )
}

@Preview(showBackground = true)
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

@Preview(showBackground = true)
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