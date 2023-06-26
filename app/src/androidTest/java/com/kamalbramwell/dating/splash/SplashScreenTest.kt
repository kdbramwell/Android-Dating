package com.kamalbramwell.dating.splash

import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.utils.ComposeTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SplashScreenTest : ComposeTest() {

    @Test
    fun screen_isVisible() {
        composeTestRule.setContent {
            DatingTheme {
                SplashScreen(SplashScreenState())
            }
        }
        assertIsDisplayed(SplashScreenTestTag)
    }

    @Test
    fun whenNotLoggedIn_navigateToRegistration() {
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
    fun whenLoggedIn_navigateToHome() {
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