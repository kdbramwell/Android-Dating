package com.kamalbramwell.dating.registration.ui.onboarding

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamalbramwell.dating.di.DefaultDispatcher
import com.kamalbramwell.dating.user.data.UserProfileDataSource
import com.kamalbramwell.dating.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingState(
    val questions: List<Question> = listOf(),
    val isLoading: Boolean = false,
    val validationError: UiText? = null,
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

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataSource: UserProfileDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private var questions = dataSource.profileQuestions

    private val _uiState = MutableStateFlow(OnboardingState(questions))
    val uiState = _uiState.asStateFlow()

    private val blankInputError = UiText.DynamicString("Cannot be blank")
    private fun ShortResponseQuestion.isBlank() = response.text.isBlank()

    fun onResponse(index: Int, value: TextFieldValue) {
        val question = questions[index] as? ShortResponseQuestion
        question?.copy(response = value)?.let { answeredQuestion ->
            questions = questions.mapIndexed { idx, q ->
                if (idx == index) answeredQuestion else q
            }
            _uiState.update {
                it.copy(
                    questions = questions,
                    navigateToNext = index == questions.size-1,
                    validationError = if (answeredQuestion.isBlank()) blankInputError else null
                )
            }
        }
    }

    private val maxOptionsExceededError = UiText.DynamicString("Too many options selected")
    private fun MultipleChoice.isOverLimit() = options.filter { it.isSelected }.size > maxSelections

    fun onChoiceClicked(index: Int, option: MultipleChoiceOption) {
        (questions[index] as? MultipleChoiceQuestion)?.let { question ->
            val updatedOptions = question.options.map {
                when {
                    it == option -> it.copy(isSelected = !it.isSelected)
                    question.maxSelections == 1 -> it.copy(isSelected = false)
                    else -> it
                }
            }
            questions = questions.mapIndexed { idx, q ->
                if (idx == index) question.copy(options = updatedOptions) else q
            }
            _uiState.update {
                it.copy(
                    questions = questions,
                    validationError = if (question.isOverLimit()) maxOptionsExceededError else null
                )
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