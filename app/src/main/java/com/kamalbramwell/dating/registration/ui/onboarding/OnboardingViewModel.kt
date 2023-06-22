package com.kamalbramwell.dating.registration.ui.onboarding

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.kamalbramwell.dating.user.data.DummyUserProfileDataSource
import com.kamalbramwell.dating.user.data.UserProfileDataSource
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

class OnboardingViewModel(
    dataSource: UserProfileDataSource = DummyUserProfileDataSource()
): ViewModel() {

    private val questions = dataSource.profileQuestions.toMutableList()

    private val _uiState = MutableStateFlow(OnboardingState(questions))
    val uiState = _uiState.asStateFlow()

    fun onResponse(index: Int, value: TextFieldValue) {
        val question = questions[index] as? ShortResponseQuestion
        question?.copy(response = value)?.let { answeredQuestion ->
            questions[index] = answeredQuestion
            _uiState.update {
                it.copy(questions = questions)
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

    fun onComplete() {
        _uiState.update {
            it.copy(navigateToNext = true)
        }
    }
}