package com.kamalbramwell.dating.registration.ui.onboarding

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamalbramwell.dating.di.IoDispatcher
import com.kamalbramwell.dating.user.data.UserProfileDataSource
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingState(
    val questions: List<Question> = listOf(),
    val isLoading: Boolean = false,
    val submissionError: UiText? = null,
    val navigateToNext: Boolean = false,
)

data class ShortResponseQuestion(
    override val prompt: UiText,
    override val response: TextFieldValue = TextFieldValue()
) : ShortResponse {
    override val isAnswered: Boolean
        get() = response.text.isNotBlank()
}

data class MultipleChoiceQuestion(
    override val prompt: UiText,
    override val options: List<MultipleChoiceOption>,
    override val maxSelections: Int,
    override val minSelections: Int
) : MultipleChoice {
    override val isAnswered: Boolean
        get() = options.any { it.isSelected }
}

class OnboardingViewModel @Inject constructor(
    private val dataSource: UserProfileDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val questions = dataSource.profileQuestions.toMutableList()

    private val _uiState = MutableStateFlow(OnboardingState(questions))
    val uiState = _uiState.asStateFlow()

    fun onResponse(index: Int, value: TextFieldValue) {
        val question = questions[index] as? ShortResponseQuestion
        question?.copy(response = value)?.let { answeredQuestion ->
            questions[index] = answeredQuestion
            _uiState.update {
                it.copy(
                    questions = questions,
                    navigateToNext = index == questions.size-1
                )
            }
        }
    }

    fun onChoiceClicked(index: Int, option: MultipleChoiceOption) {
        (questions[index] as? MultipleChoiceQuestion)?.let { question ->
            val updatedOptions = question.options.map {
                if (it == option) it.copy(isSelected = !it.isSelected)
                else it
            }
            questions[index] = question.copy(options = updatedOptions)
            _uiState.update {
                it.copy(questions = questions)
            }
        }
    }

    fun onSubmit() {
        _uiState.update {
            it.copy(isLoading = true, submissionError = null)
        }

        viewModelScope.launch(dispatcher) {
            val result = dataSource.submit(questions)
            if (result.isSuccess) {
                _uiState.update { it.copy(navigateToNext = true, isLoading = false) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        submissionError = UiText.DynamicString(
                            result.exceptionOrNull()?.message ?: "Please try again"
                        )
                    )
                }
            }
        }
    }
}