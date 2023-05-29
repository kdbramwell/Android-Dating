package com.kamalbramwell.dating.registration.ui

import androidx.compose.ui.test.performClick
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
        assertIsDisplayed(RegistrationOptionsTestTag)
    }

    @Test
    fun clickRegisterEmailButton_navigateToRegistration() {
        withText(R.string.registration_email_registration_cta).performClick()
        assertEquals(true, registerEmailClicked)
    }

    @Test
    fun clickRegisterPhoneButton_navigateToRegistration() {
        withText(R.string.registration_phone_registration_cta).performClick()
        assertEquals(true, registerPhoneClicked)
    }

    @Test
    fun clickLoginButton_navigateToLogin() {
        withText(R.string.registration_already_registered_cta).performClick()
        assertEquals(true, loginClicked)
    }
}