package com.kamalbramwell.dating.utils

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule

/**
 * Superclass for classes that test Composable screens.
 * Provides convenience test functions.
 */
abstract class ComposeTest {

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    protected fun performClick(@StringRes buttonLabel: Int) {
        composeTestRule.performClick(buttonLabel)
    }

    private inline fun <reified A: Activity> AndroidComposeTestRule<ActivityScenarioRule<A>, *>.performClick(
        @StringRes label: Int
    ) {
        onNodeWithText(
            activity.getString(label),
            useUnmergedTree = true
        ).performClick()
    }
}