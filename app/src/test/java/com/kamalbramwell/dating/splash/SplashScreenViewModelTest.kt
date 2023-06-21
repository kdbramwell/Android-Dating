package com.kamalbramwell.dating.splash

import com.kamalbramwell.dating.registration.data.FakeAuthDataSource
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SplashScreenViewModelTest {

    @Test
    fun `navigate to home if logged in`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val viewModel = SplashScreenViewModel(
            FakeAuthDataSource(true),
            dispatcher
        )

        advanceUntilIdle()

        with (viewModel.uiState.value) {
            assertEquals(true, navigateToHome)
            assertEquals(false, navigateToRegistration)
        }
    }

    @Test
    fun `navigate to registration if not logged in`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val viewModel = SplashScreenViewModel(
            FakeAuthDataSource(false),
            dispatcher
        )

        advanceUntilIdle()

        with (viewModel.uiState.value) {
            assertEquals(false, navigateToHome)
            assertEquals(true, navigateToRegistration)
        }
    }
}