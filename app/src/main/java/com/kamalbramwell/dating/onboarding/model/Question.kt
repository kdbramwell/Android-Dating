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