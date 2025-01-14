package com.kamalbramwell.dating.auth.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.auth.data.AuthUseCase
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthViewModelTest {

    private val testInput = "helloworld"
    private lateinit var viewModel: AuthViewModel

    private val dummyUseCase = object : AuthUseCase {
        override suspend fun submit(account: String, password: String): Result<Boolean> =
            Result.success(true)
    }

    @Before
    fun setUp() {
        viewModel = LoginViewModel(
            dummyUseCase,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `update state when account input is received`() = runTest {
        viewModel.onEmailOrPhoneInput(TextFieldValue(testInput))
        assertEquals(testInput, viewModel.uiState.value.emailOrPhone.text)
    }

    @Test
    fun `update state when password input is received`() = runTest {
        viewModel.onPasswordInput(TextFieldValue(testInput))
        assertEquals(testInput, viewModel.uiState.value.password.text)
    }


    @Test
    fun `update state when onNextClicked is called`() = runTest {
        viewModel.onNextClicked()
        viewModel.uiState.value.run {
            assertTrue(
                emailOrPhoneError != null || passwordError != null || isLoading || registrationSuccessful
            )
        }
    }
}