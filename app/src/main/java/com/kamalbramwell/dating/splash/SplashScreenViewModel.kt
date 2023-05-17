package com.kamalbramwell.dating.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SplashScreenState(
    val navigateToRegistration: Boolean = false,
    val navigateToHome: Boolean = false
)

class SplashScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SplashScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2)
            onRegistrationRequired()
        }
    }

    private fun onAccountFound() {
        _uiState.update {
            SplashScreenState(false, true)
        }
    }

    private fun onRegistrationRequired() {
        _uiState.update {
            SplashScreenState(true, false)
        }
    }
}