package com.kamalbramwell.dating.registration.ui.onboarding

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OnboardingState(
    val questions: List<ProfileQuestion> = listOf(),
    val isLoading: Boolean = false,
    val nextEnabled: Boolean = false,
    val navigateToNext: Boolean = false,
)

private val sampleQuestions = listOf(
    "What's your first name?",
    "What's your last name?",
    "When were you born?",
    "What's your personality type?",
)

fun generateSamples(): List<ProfileQuestion> = sampleQuestions.map {
    ProfileQuestion(UiText.DynamicString(it))
}

data class ProfileQuestion(
    val prompt: UiText,
    val response: TextFieldValue = TextFieldValue()
)

class OnboardingViewModel : ViewModel() {

    private val questions = generateSamples().toMutableList()

    private val _uiState = MutableStateFlow(OnboardingState(questions))
    val uiState = _uiState.asStateFlow()

    fun onResponse(index: Int, value: TextFieldValue) {
        questions[index] = questions[index].copy(response = value)
        _uiState.update {
            it.copy(
                questions = questions,
                nextEnabled = value.text.isNotEmpty()
            )
        }
    }

    fun onComplete() {
        _uiState.update {
            it.copy(isLoading = true)
        }
    }
}