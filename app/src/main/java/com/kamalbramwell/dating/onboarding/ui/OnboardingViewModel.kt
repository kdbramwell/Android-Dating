package com.kamalbramwell.dating.onboarding.ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.di.DefaultDispatcher
import com.kamalbramwell.dating.onboarding.model.MultipleChoice
import com.kamalbramwell.dating.onboarding.model.MultipleChoiceOption
import com.kamalbramwell.dating.onboarding.model.MultipleChoiceQuestion
import com.kamalbramwell.dating.onboarding.model.Question
import com.kamalbramwell.dating.onboarding.model.ShortResponseQuestion
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
    val submissionError: UiText? = null,
    val navigateToNext: Boolean = false,
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataSource: UserProfileDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private var questions = dataSource.profileQuestions

    private val _uiState = MutableStateFlow(OnboardingState(questions))
    val uiState = _uiState.asStateFlow()

    private val blankInputError = UiText.StringResource(R.string.error_onboarding_blank_input)

    fun onResponse(index: Int, value: TextFieldValue) {
        val question = questions[index] as? ShortResponseQuestion
        question?.copy(
            response = value,
            validationError = if (value.text.isBlank()) blankInputError else null
        )?.let { answeredQuestion ->
            questions = questions.mapIndexed { idx, q ->
                if (idx == index) answeredQuestion else q
            }
            _uiState.update {
                it.copy(
                    questions = questions,
                    navigateToNext = index == questions.size-1,
                )
            }
        }
    }

    private val maxOptionsExceededError =
        UiText.StringResource(R.string.error_onboarding_max_selections_exceeded)
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
                if (idx == index) {
                    question.copy(
                        options = updatedOptions,
                        validationError = if (question.isOverLimit()) maxOptionsExceededError else null
                    )
                } else q
            }
            _uiState.update { it.copy(questions = questions) }
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
                        submissionError = result.exceptionOrNull()?.let { exception ->
                            exception.message?.let { errorMsg -> UiText.DynamicString(errorMsg) }
                                ?: UiText.StringResource(R.string.error_onboarding_generic)
                        }
                    )
                }
            }
        }
    }
}