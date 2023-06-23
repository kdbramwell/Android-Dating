package com.kamalbramwell.dating.registration.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoice
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoiceOption
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoiceQuestion
import com.kamalbramwell.dating.registration.ui.onboarding.OnboardingViewModel
import com.kamalbramwell.dating.registration.ui.onboarding.ShortResponse
import com.kamalbramwell.dating.user.data.DummyUserProfileDataSource
import com.kamalbramwell.dating.user.data.generateMCSamples
import com.kamalbramwell.dating.utils.UiText
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

    @Test
    fun `submitting blank short response updates validation error in state`() {
        val viewModel = OnboardingViewModel(
            DummyUserProfileDataSource(shortResponse = true),
            StandardTestDispatcher()
        )
        viewModel.onResponse(0, TextFieldValue("  "))
        assertEquals(true, viewModel.uiState.value.validationError != null)
    }

    @Test
    fun `exceeding maxSelections on mc question updates validation error in state`() {
        val mcQuestions = List(5) {
            MultipleChoiceQuestion(
                prompt = UiText.DynamicString("Question"),
                options = List (4) {
                    MultipleChoiceOption(UiText.DynamicString("Option $it"))
                },
                maxSelections = 2,
                minSelections = 1
            )
        }
        val viewModel = OnboardingViewModel(
            DummyUserProfileDataSource(override = mcQuestions),
            StandardTestDispatcher()
        )
        mcQuestions.first().options.forEach {
            viewModel.onChoiceClicked(0, it)
        }
        assertEquals(true, viewModel.uiState.value.validationError != null)
    }

    @Test
    fun `prevent selecting more than 1 option when maxSelections=1`() {
        val mcQuestions = List(1) {
            MultipleChoiceQuestion(
                prompt = UiText.DynamicString("Question"),
                options = List (4) {
                    MultipleChoiceOption(UiText.DynamicString("Option $it"))
                },
                maxSelections = 1,
                minSelections = 1
            )
        }
        val viewModel = OnboardingViewModel(
            DummyUserProfileDataSource(override = mcQuestions),
            StandardTestDispatcher()
        )
        mcQuestions.first().options.forEach {
            viewModel.onChoiceClicked(0, it)
        }
        (viewModel.uiState.value.questions.first() as MultipleChoice)
            .options
            .filter { it.isSelected }
            .let { assertEquals(true, it.size == 1) }
    }
}