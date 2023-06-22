package com.kamalbramwell.dating.registration.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoice
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoiceOption
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoiceQuestion
import com.kamalbramwell.dating.registration.ui.onboarding.ShortResponse
import com.kamalbramwell.dating.registration.ui.onboarding.ShortResponseQuestion
import com.kamalbramwell.dating.ui.components.DatingText
import com.kamalbramwell.dating.ui.components.InputField
import com.kamalbramwell.dating.ui.components.rememberBrandGradient
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.user.data.sampleSrQuestions
import com.kamalbramwell.dating.utils.UiText

@Composable
fun ShortResponseItem(
    item: ShortResponse,
    modifier: Modifier = Modifier,
    onInput: (TextFieldValue) -> Unit = {}
) {
    var response by remember { mutableStateOf(item.response) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        DatingText(
            text = item.prompt,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        InputField(
            textFieldValue = response,
            onTextChanged = {
                onInput(it)
                response = it
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            ),
            placeholder = UiText.DynamicString("Your name"),
            modifier = Modifier.padding(8.dp),
        )
    }
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
        DatingText(
            text = item.prompt,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (item.maxSelections > 1) {
            DatingText(
                text = UiText.StringResource(
                    R.string.onboarding_multiple_choice_label,
                    item.maxSelections
                ),
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

/** Preview helpers **/

val dummyShortResponseQuestion = ShortResponseQuestion(
    UiText.DynamicString(sampleSrQuestions.first()),
    response = TextFieldValue("Kamal Bramwell")
)

val dummyMultipleChoiceOptions = listOf("Male", "Female", "Nonbinary").map {
    MultipleChoiceOption(UiText.DynamicString(it))
}

val dummyMultipleChoiceQuestion = MultipleChoiceQuestion(
    UiText.DynamicString(sampleSrQuestions[3]),
    options = dummyMultipleChoiceOptions,
    maxSelections = 1,
    minSelections = 1
)

@Preview(showBackground = true)
@Composable
private fun ShortResponseQuestionPreview() {
    DatingTheme {
        ShortResponseItem(item = dummyShortResponseQuestion)
    }
}

@Preview(showBackground = true)
@Composable
private fun MultipleChoiceItemPreview() {
    DatingTheme {
        var question by remember { mutableStateOf(dummyMultipleChoiceQuestion) }
        MultipleChoiceItem(
            item = question,
            onClick = { option ->
                val clickedOption = option.copy(isSelected = !option.isSelected)
                val updatedOptions = question.options.map {
                    if (it == option) clickedOption else it
                }
                question = question.copy(options = updatedOptions)
            }
        )
    }
}