package com.kamalbramwell.dating.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamalbramwell.dating.di.DefaultDispatcher
import com.kamalbramwell.dating.registration.data.AuthDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashScreenState(
    val navigateToRegistration: Boolean = false,
    val navigateToHome: Boolean = false
)

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val dataSource: AuthDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            delay(2_000)
            checkLoginStatus()
        }
    }

    private suspend fun checkLoginStatus() {
        dataSource.isLoggedIn.collect { loggedIn ->
            if (loggedIn) onAccountFound()
            else onRegistrationRequired()
        }
    }

    private fun onAccountFound() {
        _uiState.update {
            SplashScreenState(navigateToRegistration = false, navigateToHome = true)
        }
    }

    private fun onRegistrationRequired() {
        _uiState.update {
            SplashScreenState(navigateToRegistration = true, navigateToHome = false)
        }
    }
}