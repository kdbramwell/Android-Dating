package com.kamalbramwell.dating.splash

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kamalbramwell.dating.ui.theme.DatingTheme
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun backgroundIsVisible() {
        composeTestRule.setContent {
            DatingTheme {
                SplashScreen(SplashScreenState())
            }
        }
        composeTestRule.onNodeWithTag(BackgroundTestTag).assertIsDisplayed()
    }

    @Test
    fun navigateToRegistrationWhenNotRegistered() {
        var navigatedToRegistration = false
        composeTestRule.setContent {
            DatingTheme {
                SplashScreen(
                    uiState = SplashScreenState(navigateToRegistration = true),
                    onNavigateToRegistration = { navigatedToRegistration = true }
                )
            }
        }

        assertEquals(true, navigatedToRegistration)
    }

    @Test
    fun navigateToHomeWhenRegistered() {
        var navigatedToHome = false
        composeTestRule.setContent {
            DatingTheme {
                SplashScreen(
                    uiState = SplashScreenState(navigateToHome = true),
                    onNavigateToHome = { navigatedToHome = true }
                )
            }
        }

        assertEquals(true, navigatedToHome)
    }
}