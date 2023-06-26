package com.kamalbramwell.dating.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.utils.ComposeTest
import com.kamalbramwell.dating.utils.UiText
import org.junit.Test

class InputFieldTest : ComposeTest() {

    private val testInput = TextFieldValue("hello world")
    private val testError = UiText.DynamicString("Exception occurred.")
    private val testLabel = UiText.DynamicString("Enter message")
    private val testPlaceholder = UiText.DynamicString("Lorem ipsum")

    @Test
    fun noInput_noError_placeholderIsShown() {
        composeTestRule.setContent {
            InputField(
                textFieldValue = TextFieldValue(),
                error = null,
                label = testLabel,
                placeholder = testPlaceholder
            )
        }
        withTag(InputFieldPlaceholderTestTag).assertIsDisplayed()
    }

    @Test
    fun hasInput_hasFocus_labelIsShown() {
        composeTestRule.setContent {
            InputField(
                textFieldValue = testInput,
                error = testError,
                label = testLabel,
                placeholder = testPlaceholder
            )
        }
        withTag(InputFieldTextFieldTestTag).performClick()
        withTag(InputFieldLabelTestTag).assertIsDisplayed()
    }
}