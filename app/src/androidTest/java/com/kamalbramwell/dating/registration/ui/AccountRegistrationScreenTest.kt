package com.kamalbramwell.dating.registration.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.utils.ComposeTest
import com.kamalbramwell.dating.utils.UiText
import org.junit.Assert.assertEquals
import org.junit.Test

class AccountRegistrationScreenTest : ComposeTest() {

    private val testInput = "helloWorld"
    private val testError = UiText.DynamicString("Error")

    @Test
    fun accountField_showsEmailOrPhoneInput() {
        val testState = RegistrationState(emailOrPhone = TextFieldValue(testInput))
        composeTestRule.setContent {
            AccountRegistrationScreen(testState)
        }
        withText(testInput).assertIsDisplayed()
    }

    @Test
    fun passwordField_showsPasswordInput() {
        val testState = RegistrationState(password = TextFieldValue(testInput))
        composeTestRule.setContent {
            AccountRegistrationScreen(testState)
        }
        withText(testInput).assertIsDisplayed()
    }

    @Test
    fun accountField_showsEmailOrPhoneError() {
        val testState = RegistrationState(emailOrPhoneError = testError)
        composeTestRule.setContent {
            AccountRegistrationScreen(testState)
        }
        withText(testError.value).assertIsDisplayed()
    }

    @Test
    fun passwordField_showsPasswordError() {
        val testState = RegistrationState(passwordError = testError)
        composeTestRule.setContent {
            AccountRegistrationScreen(testState)
        }
        withText(testError.value).assertIsDisplayed()
    }

    @Test
    fun nextButton_showsEnabledState() {
        val testState = RegistrationState(nextButtonEnabled = true)
        composeTestRule.setContent {
            AccountRegistrationScreen(testState)
        }
        withText(R.string.registration_next).assertIsEnabled()
    }

    @Test
    fun nextButton_showsDisabledState() {
        val testState = RegistrationState(nextButtonEnabled = false)
        composeTestRule.setContent {
            AccountRegistrationScreen(testState)
        }
        withText(R.string.registration_next).assertIsNotEnabled()
    }

    @Test
    fun clickNextButton_callsOnNextClicked() {
        var nextClicked = false
        composeTestRule.setContent {
            AccountRegistrationScreen(
                uiState = RegistrationState(nextButtonEnabled = true),
                onNextClicked = { nextClicked = true }
            )
        }
        withText(R.string.registration_next).performClick()
        assertEquals(true, nextClicked)
    }

    @Test
    fun clickCancelButton_callsOnCancelClicked() {
        var cancelClicked = false
        composeTestRule.setContent {
            AccountRegistrationScreen(
                uiState = RegistrationState(),
                onCancelClicked = { cancelClicked = true }
            )
        }
        withText(R.string.registration_back).performClick()
        assertEquals(true, cancelClicked)
    }
}