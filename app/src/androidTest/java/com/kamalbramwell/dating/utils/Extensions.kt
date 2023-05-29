package com.kamalbramwell.dating.utils

import android.app.Activity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule

inline fun <reified A: Activity> AndroidComposeTestRule<ActivityScenarioRule<A>, *>.performClick(
    @StringRes label: Int
) {
    onNodeWithText(
        activity.getString(label),
        useUnmergedTree = true
    ).performClick()
}