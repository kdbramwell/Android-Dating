package com.kamalbramwell.dating.utils

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule

/**
 * Superclass for classes that test Composable screens.
 * Provides convenience test functions.
 */
abstract class ComposeTest {

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    protected fun assertIsDisplayed(testTag: String) {
        withTag(testTag).assertIsDisplayed()
    }

    protected fun withText(@StringRes label: Int): SemanticsNodeInteraction = with (composeTestRule) {
        onNodeWithText(
            text = activity.getString(label),
            useUnmergedTree = false
        )
    }

    protected fun withText(text: String): SemanticsNodeInteraction = with (composeTestRule) {
        onNodeWithText(
            text = text,
            useUnmergedTree = false
        )
    }

    protected fun withTag(testTag: String): SemanticsNodeInteraction = with (composeTestRule) {
        onNodeWithTag(
            testTag = testTag,
            useUnmergedTree = false
        )
    }

    protected fun withDescription(@StringRes label: Int): SemanticsNodeInteraction = with (composeTestRule) {
        onNodeWithContentDescription(
            label = activity.getString(label),
            useUnmergedTree = false
        )
    }
}