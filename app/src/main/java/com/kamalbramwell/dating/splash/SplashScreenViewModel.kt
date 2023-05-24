package com.kamalbramwell.dating.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.kamalbramwell.dating.DatingApplication
import com.kamalbramwell.dating.registration.data.AccountDataSource
import com.kamalbramwell.dating.registration.data.LocalAccountDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SplashScreenState(
    val navigateToRegistration: Boolean = false,
    val navigateToHome: Boolean = false
)

class SplashScreenViewModel(
    app: Application,
    private val dataSource: AccountDataSource = LocalAccountDataSource(app)
) : AndroidViewModel(app) {

    private val _uiState = MutableStateFlow(SplashScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2_000)
            checkLoginStatus()
        }
    }

    private suspend fun checkLoginStatus() {
        dataSource.isLoggedIn.collectLatest {  loggedIn ->
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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return SplashScreenViewModel(
                    (application as DatingApplication),
                    LocalAccountDataSource(application)
                ) as T
            }
        }
    }
}