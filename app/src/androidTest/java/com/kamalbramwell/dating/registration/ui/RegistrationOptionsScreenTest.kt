package com.kamalbramwell.dating.registration.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.utils.ComposeTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegistrationOptionsScreenTest : ComposeTest() {

    private var registerEmailClicked = false
    private var registerPhoneClicked = false
    private var loginClicked = false

    @Before
    fun setUpScreen() {
        registerEmailClicked = false
        registerPhoneClicked = false
        loginClicked = false

        composeTestRule.setContent {
            DatingTheme {
                RegistrationOptionsScreen(
                    onEmailRegistrationClicked = { registerEmailClicked = true },
                    onPhoneRegistrationClicked = { registerPhoneClicked = true },
                    onAlreadyRegisteredClicked = { loginClicked = true }
                )
            }
        }
    }

    @Test
    fun screen_isVisible() {
        composeTestRule.onNodeWithTag(RegistrationOptionsTestTag).assertIsDisplayed()
    }

    @Test
    fun clickRegisterEmailButton_navigateToRegistration() {
        performClick(R.string.registration_email_registration_cta)
        assertEquals(true, registerEmailClicked)
    }

    @Test
    fun clickRegisterPhoneButton_navigateToRegistration() {
        performClick(R.string.registration_phone_registration_cta)
        assertEquals(true, registerPhoneClicked)
    }

    @Test
    fun clickLoginButton_navigateToLogin() {
        performClick(R.string.registration_already_registered_cta)
        assertEquals(true, loginClicked)
    }
}