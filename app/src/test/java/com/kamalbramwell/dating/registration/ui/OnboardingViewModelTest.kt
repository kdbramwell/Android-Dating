package com.kamalbramwell.dating.registration.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoice
import com.kamalbramwell.dating.registration.ui.onboarding.OnboardingViewModel
import com.kamalbramwell.dating.registration.ui.onboarding.ShortResponse
import com.kamalbramwell.dating.user.data.DummyUserProfileDataSource
import com.kamalbramwell.dating.user.data.generateMCSamples
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class OnboardingViewModelTest {

    @Test
    fun `update state when response is received`() {
        val viewModel = OnboardingViewModel(
            DummyUserProfileDataSource(shortResponse = true),
            StandardTestDispatcher()
        )

        val questionIndex = 1
        val providedResponse = "hello world"
        viewModel.onResponse(questionIndex, TextFieldValue(providedResponse))
        val updatedQuestion =
            viewModel.uiState.value.questions[questionIndex] as ShortResponse

        with (updatedQuestion.response) {
            assertEquals(text, providedResponse)
        }
    }

    @Test
    fun `update state when multiple choice option is clicked`() {
        val viewModel = OnboardingViewModel(
            DummyUserProfileDataSource(multipleChoice = true),
            StandardTestDispatcher()
        )

        val questionIndex = 1
        val selectedOptionIndex = 1
        val questions = generateMCSamples()
        val clickedOption = questions[questionIndex].options[selectedOptionIndex]

        viewModel.onChoiceClicked(questionIndex, clickedOption)
        val updatedQuestion =
            viewModel.uiState.value.questions[questionIndex] as MultipleChoice

        with (updatedQuestion.options[selectedOptionIndex]) {
            assertEquals(true, isSelected)
        }
    }

    @Test
    fun `successful submission updates navigateToNext to true`() = runTest {
        val viewModel = OnboardingViewModel(
            DummyUserProfileDataSource(
                shortResponse = true,
                answered = true,
                dispatcher = StandardTestDispatcher(testScheduler)
            ),
            StandardTestDispatcher(testScheduler)
        )

        viewModel.onSubmit()
        advanceUntilIdle()
        assertEquals(true, viewModel.uiState.value.navigateToNext)
    }

    @Test
    fun `error during submission is exposed in state`() = runTest {
        val viewModel = OnboardingViewModel(
            DummyUserProfileDataSource(
                shortResponse = true,
                answered = false,
                dispatcher = StandardTestDispatcher(testScheduler)
            ),
            StandardTestDispatcher(testScheduler)
        )

        viewModel.onSubmit()
        advanceUntilIdle()
        assertEquals(true, viewModel.uiState.value.submissionError != null)
    }

}