package com.kamalbramwell.dating.registration.ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RegistrationState(
    val emailOrPhone: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val emailOrPhoneError: UiText? = null,
    val passwordError: UiText? = null,
    val nextButtonEnabled: Boolean = false
)

class RegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState())
    val uiState = _uiState.asStateFlow()

    fun onEmailOrPhoneInput(textFieldValue: TextFieldValue) {

    }

    fun onPasswordInput(textFieldValue: TextFieldValue) {

    }

    fun onNextClicked() {}

    private fun onPasswordInvalid() {

    }

    private fun validateAsEmail(text: String): Boolean {
        return false
    }

    private fun validateAsPhone(text: String): Boolean {
        return false
    }
}