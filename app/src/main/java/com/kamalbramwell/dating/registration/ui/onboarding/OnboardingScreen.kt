package com.kamalbramwell.dating.registration.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.registration.ui.components.Heading
import com.kamalbramwell.dating.ui.components.BackButton
import com.kamalbramwell.dating.ui.components.DatingText
import com.kamalbramwell.dating.ui.components.InputField
import com.kamalbramwell.dating.ui.components.NextButton
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.utils.UiText

const val OnboardingTestTag = "Onboarding"

@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

}

@Composable
fun OnboardingScreen(
    uiState: OnboardingState = OnboardingState(),
    onNextClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(defaultContentPadding)
            .semantics { testTag = OnboardingTestTag },
    ) {
        Heading(text = UiText.StringResource(R.string.onboarding_heading))

        QuestionCards(Modifier.weight(1f))

        Row {
            BackButton(
                modifier = Modifier.padding(8.dp),
                onClick = onBackClicked
            )

            Spacer(Modifier.weight(1f))

            NextButton(
                modifier = Modifier.padding(8.dp),
                onClick = onNextClicked
            )
        }
    }
}

private val sampleQuestions = listOf(
    "What's your first name?",
    "What's your last name?",
    "When were you born?",
    "What's your personality type?",
)

private fun generateSamples(): List<ProfileQuestion> = sampleQuestions.map {
    ProfileQuestion(UiText.DynamicString(it))
}

@Composable
private fun QuestionCards(
    modifier: Modifier = Modifier,
    questions: List<ProfileQuestion> = generateSamples(),
    scrollState: LazyListState = rememberLazyListState(),
) {
    LazyRow(
        modifier = modifier,
        state = scrollState,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(items = questions) { question ->
            QuestionItem(
                modifier = Modifier.fillParentMaxWidth(1f),
                item = question
            )
        }
    }
}

@Composable
private fun QuestionItem(
    item: ProfileQuestion,
    modifier: Modifier = Modifier
) {
    var response by remember { mutableStateOf(TextFieldValue())}

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(24.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            verticalArrangement = Arrangement.Center
        ) {
            DatingText(
                text = item.text,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            InputField(
                textFieldValue = response,
                onTextChanged = { response = it },
                placeholder = UiText.DynamicString("Your name"),
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    DatingTheme {
        OnboardingScreen(OnboardingState())
    }
}

@Preview
@Composable
private fun OnboardingScreenDarkPreview() {
    DatingTheme(darkTheme = true) {
        OnboardingScreen(OnboardingState())
    }
}