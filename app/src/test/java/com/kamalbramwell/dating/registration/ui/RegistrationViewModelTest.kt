package com.kamalbramwell.dating.registration.ui

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegistrationViewModelTest {

    private val testInput = "helloworld"
    private lateinit var viewModel: RegistrationViewModel

    @Before
    fun setUp() {
        viewModel = RegistrationViewModel()
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