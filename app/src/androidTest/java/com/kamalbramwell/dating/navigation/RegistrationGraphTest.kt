package com.kamalbramwell.dating.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.navigation.graphs.registration.Registration
import com.kamalbramwell.dating.utils.ComposeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Checks that the navigation flows during registration/login are correct.
 */
@HiltAndroidTest
class RegistrationGraphTest : ComposeTest() {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    lateinit var navController: TestNavHostController

    @Before
    fun setUpNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            DatingNavHost(navController)

            navController.setCurrentDestination(Registration.route)
        }
    }

    @Test
    fun registration_clickEmail_navigateToEmailRegistration() {
        withText(R.string.registration_email_registration_cta).performClick()
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(Registration.EmailRegistration.route, route)
    }

//    @Test
//    fun registration_clickPhone_navigateToPhoneRegistration() {
//
//    }
//
//    @Test
//    fun registration_clickLogin_navigateToLogin() {
//
//    }

//    @Test
//    fun emailRegistration_clickContinue_navigateToHome() {
//
//    }
//
//    @Test
//    fun emailRegistration_back_registration() {
//
//    }
//
//    @Test
//    fun phoneRegistration_clickContinue_navigateToHome() {
//
//    }
//
//    @Test
//    fun phoneRegistration_back_registration() {
//
//    }
//
//    @Test
//    fun login_clickSubmit_navigateToHome() {
//
//    }
//
//    @Test
//    fun login_back_registration() {
//
//    }
}