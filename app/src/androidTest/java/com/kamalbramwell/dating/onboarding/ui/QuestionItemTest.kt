package com.kamalbramwell.dating.onboarding.ui

import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.onboarding.model.MultipleChoiceOption
import com.kamalbramwell.dating.onboarding.model.ShortResponseQuestion
import com.kamalbramwell.dating.onboarding.ui.components.MultipleChoiceItem
import com.kamalbramwell.dating.onboarding.ui.components.ShortResponseItem
import com.kamalbramwell.dating.ui.components.InputFieldTextFieldTestTag
import com.kamalbramwell.dating.user.data.generateMCSamples
import com.kamalbramwell.dating.user.data.generateShortResponseSamples
import com.kamalbramwell.dating.utils.ComposeTest
import org.junit.Assert.assertEquals
import org.junit.Test

class QuestionItemTest : ComposeTest() {

    @Test
    fun shortResponse_enterInput_onInputIsCalled() {
        val testItem = (generateShortResponseSamples().random() as ShortResponseQuestion)
            .copy(response = TextFieldValue())
        val testInput = "hello world"
        var receivedInput: TextFieldValue? = null

        composeTestRule.setContent {
            ShortResponseItem(
                item = testItem,
                onInput = { receivedInput = it }
            )
        }
        withTag(InputFieldTextFieldTestTag)
            .performClick()
            .performTextInput(testInput)

        assertEquals(testInput, receivedInput?.text ?: "")
    }

    @Test
    fun multipleChoice_selectOption_OnClickIsCalled() {
        val testItem = generateMCSamples().random()
        var receivedOption: MultipleChoiceOption? = null
        var clickedOptionLabel = ""
        composeTestRule.setContent {
            MultipleChoiceItem(
                item = testItem,
                onClick = {
                    receivedOption = it
                }
            )
            clickedOptionLabel = testItem.options.first().label.asString()
        }
        composeTestRule.onAllNodesWithText(clickedOptionLabel)
            .onLast()
            .performClick()

        assertEquals(testItem.options.first(), receivedOption)
    }
}