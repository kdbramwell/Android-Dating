package com.kamalbramwell.dating.splash

import com.kamalbramwell.dating.registration.data.FakeAuthDataSource
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class SplashScreenViewModelTest {

    @Test
    fun `navigate to home if logged in`() = runTest {
        val viewModel = SplashScreenViewModel(
            FakeAuthDataSource(true),
            UnconfinedTestDispatcher()
        )

        with (viewModel.uiState.value) {
            assertEquals(true, navigateToHome)
            assertEquals(false, navigateToRegistration)
        }
    }

    @Test
    fun `navigate to registration if not logged in`() = runTest {
        val viewModel = SplashScreenViewModel(
            FakeAuthDataSource(false),
            UnconfinedTestDispatcher()
        )

        with (viewModel.uiState.value) {
            assertEquals(false, navigateToHome)
            assertEquals(true, navigateToRegistration)
        }
    }
}