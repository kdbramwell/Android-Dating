package com.kamalbramwell.dating.onboarding.model

import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.utils.UiText

sealed interface Question {
    val prompt: UiText
    val isAnswered: Boolean
    val validationError: UiText?
}

interface ShortResponse : Question {
    val hint: UiText?
    val response: TextFieldValue
}

interface MultipleChoice : Question {
    val options: List<MultipleChoiceOption>
    val maxSelections: Int
    val minSelections: Int
}

data class MultipleChoiceOption(
    val label: UiText,
    val isSelected: Boolean = false
)

data class ShortResponseQuestion(
    override val prompt: UiText,
    override val response: TextFieldValue = TextFieldValue(),
    override val hint: UiText? = null,
    override val validationError: UiText? = null
) : ShortResponse {
    override val isAnswered: Boolean
        get() = response.text.isNotBlank()
}

data class MultipleChoiceQuestion(
    override val prompt: UiText,
    override val options: List<MultipleChoiceOption>,
    override val maxSelections: Int,
    override val minSelections: Int,
    override val validationError: UiText? = null
) : MultipleChoice {
    override val isAnswered: Boolean
        get() = options.any { it.isSelected }
}