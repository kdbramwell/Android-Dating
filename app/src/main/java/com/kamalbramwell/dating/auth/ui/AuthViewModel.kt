package com.kamalbramwell.dating.auth.ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.auth.data.AuthUseCase
import com.kamalbramwell.dating.di.Create
import com.kamalbramwell.dating.di.IoDispatcher
import com.kamalbramwell.dating.di.Login
import com.kamalbramwell.dating.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegistrationState(
    val heading: UiText? = null,
    val emailOrPhone: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val emailOrPhoneError: UiText? = null,
    val passwordError: UiText? = null,
    val taskError: UiText? = null,
    val isLoading: Boolean = false,
    val registrationSuccessful: Boolean = false,
)

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    @Create private val authUseCase: AuthUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AuthViewModel(
    UiText.StringResource(R.string.registration_create_account_heading),
    authUseCase,
    dispatcher
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    @Login private val authUseCase: AuthUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AuthViewModel(
    UiText.StringResource(R.string.registration_login_heading),
    authUseCase,
    dispatcher
)

open class AuthViewModel(
    heading: UiText,
    private val authUseCase: AuthUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState(heading))
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

    fun onNextClicked() = viewModelScope.launch(dispatcher) {
        _uiState.update {
            it.copy(
                isLoading = true,
                emailOrPhoneError = null,
                passwordError = null,
                taskError = null,
            )
        }

        with (uiState.value) {
            val result = authUseCase.submit(emailOrPhone.text, password.text)
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(registrationSuccessful = true)
                }
            } else {
                val error = result.exceptionOrNull()?.message?.let { UiText.DynamicString(it) }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        taskError = error
                    )
                }
            }
        }
    }
}