package com.kamalbramwell.dating.registration.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoice
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoiceOption
import com.kamalbramwell.dating.registration.ui.onboarding.ShortResponse
import com.kamalbramwell.dating.ui.components.DatingText
import com.kamalbramwell.dating.ui.components.InputField
import com.kamalbramwell.dating.ui.components.rememberBrandGradient
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
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
            modifier = Modifier.padding(8.dp)
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
            modifier = Modifier.padding(8.dp)
        )

        if (item.maxSelections > 1) {
            Spacer(Modifier.size(defaultContentPadding))

            DatingText(
                text = UiText.StringResource(
                    R.string.onboarding_multiple_choice_label,
                    item.maxSelections
                ),
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(Modifier.size(defaultContentPadding))

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
        modifier = Modifier.padding(16.dp)
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
        modifier = modifier.drawBehind {
            if (option.isSelected) {
                drawRoundRect(
                    brush = brush,
                    cornerRadius = CornerRadius(8f, 8f)
                )
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor =
                if (option.isSelected) Color.Transparent
                else MaterialTheme.colorScheme.outline
        )
    ) {
        DatingText(text = option.label)
    }
}