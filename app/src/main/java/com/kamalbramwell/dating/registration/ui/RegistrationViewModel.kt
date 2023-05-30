package com.kamalbramwell.dating.registration.ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RegistrationState(
    val emailOrPhone: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val emailOrPhoneError: UiText? = null,
    val passwordError: UiText? = null,
    val isLoading: Boolean = false,
    val nextButtonEnabled: Boolean = false,
    val registrationSuccessful: Boolean = false,
)

class RegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState())
    val uiState = _uiState.asStateFlow()

    fun onEmailOrPhoneInput(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(emailOrPhone = textFieldValue)
        }
    }

    fun onPasswordInput(textFieldValue: TextFieldValue) {
        _uiState.update {
            it.copy(password = textFieldValue)
        }
    }

    fun onNextClicked() {
        _uiState.update {
            it.copy(isLoading = true)
        }
    }
}