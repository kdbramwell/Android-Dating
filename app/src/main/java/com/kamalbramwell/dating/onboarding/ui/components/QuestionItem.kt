package com.kamalbramwell.dating.onboarding.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.onboarding.model.MultipleChoice
import com.kamalbramwell.dating.onboarding.model.MultipleChoiceOption
import com.kamalbramwell.dating.onboarding.ui.MultipleChoiceQuestion
import com.kamalbramwell.dating.onboarding.model.ShortResponse
import com.kamalbramwell.dating.ui.components.DatingText
import com.kamalbramwell.dating.ui.components.InputField
import com.kamalbramwell.dating.ui.components.rememberBrandGradient
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.user.data.generateMCSamples
import com.kamalbramwell.dating.user.data.generateShortResponseSamples
import com.kamalbramwell.dating.utils.UiText

@Composable
fun ShortResponseItem(
    item: ShortResponse,
    modifier: Modifier = Modifier,
    onInput: (TextFieldValue) -> Unit = {},
    onImeActionClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ProfileQuestion(item.prompt)

        InputField(
            textFieldValue = item.response,
            placeholder = item.hint,
            placeholderTextAlign = TextAlign.Center,
            onTextChanged = onInput,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            ),
            error = item.validationError,
            labelTextAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { onImeActionClick() }
        )
    }
}

@Composable
private fun ColumnScope.ProfileQuestion(prompt: UiText) {
    DatingText(
        text = prompt,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
            .padding(8.dp)
            .align(Alignment.CenterHorizontally)
    )
}

@Composable
fun MultipleChoiceItem(
    item: MultipleChoice,
    modifier: Modifier = Modifier,
    onClick: (MultipleChoiceOption) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ProfileQuestion(item.prompt)

        if (item.maxSelections > 1) {
            DatingText(
                text = UiText.StringResource(
                    R.string.onboarding_multiple_choice_label,
                    item.maxSelections
                ),
                color = if (item.validationError == null) {
                    MaterialTheme.colorScheme.onBackground
                } else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(vertical = defaultContentPadding)
            )
        }

        OptionsRow(item.options, onClick)
    }
}

@Composable
private fun OptionsRow(
    options: Collection<MultipleChoiceOption>,
    onClick: (MultipleChoiceOption) -> Unit = {}
) {
    SimpleFlowRow(
        verticalGap = 8.dp,
        horizontalGap = 8.dp,
        alignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        options.forEach {
            MultipleChoiceOptionItem(option = it, onClick = onClick)
        }
    }
}

@Composable
private fun MultipleChoiceOptionItem(
    option: MultipleChoiceOption,
    modifier: Modifier = Modifier,
    onClick: (MultipleChoiceOption) -> Unit = {}
) {
    val brush = rememberBrandGradient()

    Button(
        onClick = { onClick(option) },
        modifier = modifier
            .clip(ButtonDefaults.shape)
            .drawBehind {
                if (option.isSelected) {
                    drawRoundRect(
                        brush = brush,
                        cornerRadius = CornerRadius(64f, 64f)
                    )
                }
            },
        colors = ButtonDefaults.buttonColors(
            containerColor =
                if (option.isSelected) Transparent
                else MaterialTheme.colorScheme.outline
        )
    ) {
        DatingText(text = option.label)
    }
}

@Preview(showBackground = true)
@Composable
private fun ShortResponseQuestionPreview() {
    DatingTheme {
        ShortResponseItem(item = generateShortResponseSamples().random())
    }
}

@Preview(showBackground = true)
@Composable
private fun MultipleChoiceItemPreview() {
    DatingTheme {
        var question by remember {
            mutableStateOf(generateMCSamples().random())
        }
        MultipleChoiceItem(
            item = question,
            onClick = { option ->
                val clickedOption = option.copy(isSelected = !option.isSelected)
                val updatedOptions = question.options.map {
                    if (it == option) clickedOption else it
                }
                (question as? MultipleChoiceQuestion)?.let {
                    question = it.copy(options = updatedOptions)
                }
            }
        )
    }
}