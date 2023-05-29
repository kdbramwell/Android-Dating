package com.kamalbramwell.dating

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.kamalbramwell.dating.splash.BackgroundTestTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

/**
 * Checks that the navigation flows in the app are correct.
 */
@HiltAndroidTest
class AppTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun app_launches() {
        // Check app launches at the correct destination
        composeTestRule.onNodeWithTag(BackgroundTestTag).assertIsDisplayed()
    }
}