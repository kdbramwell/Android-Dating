package com.kamalbramwell.dating.registration.ui

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.platform.app.InstrumentationRegistry
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.registration.ui.components.dummyMultipleChoiceQuestion
import com.kamalbramwell.dating.registration.ui.components.dummyShortResponseQuestion
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoiceOption
import com.kamalbramwell.dating.registration.ui.onboarding.OnboardingScreen
import com.kamalbramwell.dating.registration.ui.onboarding.OnboardingState
import com.kamalbramwell.dating.registration.ui.onboarding.generateSamples
import com.kamalbramwell.dating.ui.components.InputFieldTextFieldTestTag
import com.kamalbramwell.dating.utils.ComposeTest
import org.junit.Assert.assertEquals
import org.junit.Test

class OnboardingScreenTest : ComposeTest() {

    @Test
    fun backButton_noPreviousQuestions_isDisabled() {
        val testState = OnboardingState(questions = generateSamples())
        composeTestRule.setContent {
            OnboardingScreen(testState)
        }
        withDescription(R.string.back).assertIsNotEnabled()
    }

    @Test
    fun nextButton_observesOnboardingState() {
        val testState = OnboardingState(
            questions = generateSamples(),
            nextEnabled = true
        )
        composeTestRule.setContent {
            OnboardingScreen(testState)
        }
        withDescription(R.string.next).assertIsEnabled()
    }

    @Test
    fun shortResponseInput_providesQuestionIndexAndResponse() {
        val dummyQuestion = dummyShortResponseQuestion.copy(response = TextFieldValue())
        val testState = OnboardingState(questions = List(2) { dummyQuestion })
        val startIndex = 1
        val testInput = "hello world"

        var receivedIndex = 0
        var receivedInput: TextFieldValue? = null

        composeTestRule.setContent {
            OnboardingScreen(
                uiState = testState,
                pagerState = rememberPagerState(startIndex),
                onResponse = { idx, input ->
                    receivedIndex = idx
                    receivedInput = input
                }
            )
        }
        composeTestRule.onAllNodesWithTag(InputFieldTextFieldTestTag)
            .onLast()
            .performClick()
            .performTextInput(testInput)

        assertEquals(testInput, receivedInput?.text ?: "")
        assertEquals(startIndex, receivedIndex)
    }

    @Test
    fun clickMultipleChoiceOption_providesQuestionIndexAndSelectedOption() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val dummyQuestion = dummyMultipleChoiceQuestion
        val testState = OnboardingState(questions = List(2) { dummyQuestion })
        val startIndex = 1

        var receivedIndex = 0
        var receivedOption: MultipleChoiceOption? = null

        composeTestRule.setContent {
            OnboardingScreen(
                uiState = testState,
                pagerState = rememberPagerState(startIndex),
                onOptionClick = { idx, option ->
                    receivedIndex = idx
                    receivedOption = option
                }
            )
        }
        composeTestRule.onAllNodesWithText(dummyMultipleChoiceQuestion.options.first().label.asString(context))
            .onLast()
            .performClick()

        assertEquals(startIndex, receivedIndex)
        assertEquals(dummyMultipleChoiceQuestion.options.first(), receivedOption)
    }
}