package com.kamalbramwell.dating.registration.ui.onboarding

import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.utils.UiText

sealed interface Question {
    val prompt: UiText
}

interface ShortResponse : Question {
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