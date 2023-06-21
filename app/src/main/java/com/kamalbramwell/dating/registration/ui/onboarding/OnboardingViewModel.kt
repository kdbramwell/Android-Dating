package com.kamalbramwell.dating.registration.ui.onboarding

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OnboardingState(
    val questions: List<Question> = listOf(),
    val isLoading: Boolean = false,
    val nextEnabled: Boolean = false,
    val navigateToNext: Boolean = false,
)

val sampleQuestions = listOf(
    "What's your name?",
    "When were you born?",
    "How do you identify?",
    "What are you looking for?",
    "What's your personality type?",
)

fun generateSamples(): List<Question> = sampleQuestions.map {
    ShortResponseQuestion(UiText.DynamicString(it))
}

data class ShortResponseQuestion(
    override val prompt: UiText,
    override val response: TextFieldValue = TextFieldValue()
) : ShortResponse

data class MultipleChoiceQuestion(
    override val prompt: UiText,
    override val options: List<MultipleChoiceOption>,
    override val maxSelections: Int,
    override val minSelections: Int
) : MultipleChoice

class OnboardingViewModel : ViewModel() {

    private val questions = generateSamples().toMutableList()

    private val _uiState = MutableStateFlow(OnboardingState(questions))
    val uiState = _uiState.asStateFlow()

    fun onResponse(index: Int, value: TextFieldValue) {
//        questions[index] = questions[index].copy(response = value)
//        _uiState.update {
//            it.copy(
//                questions = questions,
//                nextEnabled = value.text.isNotEmpty()
//            )
//        }
    }

    fun onChoiceClicked(index: Int, option: MultipleChoiceOption) {

    }

    fun onComplete() {
        _uiState.update {
            it.copy(navigateToNext = true)
        }
    }
}