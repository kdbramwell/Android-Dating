package com.kamalbramwell.dating.onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.onboarding.model.MultipleChoice
import com.kamalbramwell.dating.onboarding.model.MultipleChoiceOption
import com.kamalbramwell.dating.onboarding.model.Question
import com.kamalbramwell.dating.onboarding.model.ShortResponse
import com.kamalbramwell.dating.ui.components.Heading
import com.kamalbramwell.dating.onboarding.ui.components.MultipleChoiceItem
import com.kamalbramwell.dating.onboarding.ui.components.ShortResponseItem
import com.kamalbramwell.dating.ui.components.BackButton
import com.kamalbramwell.dating.ui.components.DatingText
import com.kamalbramwell.dating.ui.components.NextButton
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.user.data.generateShortResponseSamples
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.launch

const val OnboardingTestTag = "Onboarding"

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = viewModel(),
    onNavigateNext: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState()

    OnboardingScreen(
        uiState,
        pagerState,
        viewModel::onResponse,
        viewModel::onChoiceClicked,
        viewModel::onSubmit
    )

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) onNavigateNext()
    }
}

@Composable
fun OnboardingScreen(
    uiState: OnboardingState = OnboardingState(),
    pagerState: PagerState = rememberPagerState(),
    onResponse: (index: Int, value: TextFieldValue) -> Unit = { _, _ -> },
    onOptionClick: (index: Int, option: MultipleChoiceOption) -> Unit = { _, _ -> },
    onSubmit: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val backEnabled by remember(uiState.questions) {
        derivedStateOf { pagerState.currentPage > 0 }
    }
    val hasError by remember(uiState.questions) {
        derivedStateOf {
            uiState.submissionError != null
                    || uiState.questions[pagerState.currentPage].validationError != null
        }
    }
    val nextEnabled by remember(uiState.questions) {
        derivedStateOf {
            uiState.questions[pagerState.currentPage].isAnswered && !hasError
        }
    }
    val completed by remember(uiState.questions)  {
        derivedStateOf { pagerState.currentPage >= uiState.questions.size -1 }
    }
    val onNextClick: ()-> Unit = {
        coroutineScope.launch {
            if (completed) onSubmit()
            else pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }
    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(defaultContentPadding)
            .semantics { testTag = OnboardingTestTag },
    ) {
        Heading(text = UiText.StringResource(R.string.onboarding_heading))

        ProfileQuestions(
            modifier = Modifier.weight(1f),
            questions = uiState.questions,
            pagerState = pagerState,
            onResponseInput = onResponse,
            onOptionClick = onOptionClick,
            onImeActionClick = {
                if (nextEnabled) onNextClick()
                focusManager.clearFocus()
            }
        )

        uiState.submissionError?.let {
            DatingText(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Row {
            BackButton(
                enabled = backEnabled,
                onClick = {
                    coroutineScope.launch {
                          pagerState.animateScrollToPage(pagerState.currentPage -1)
                    }
                },
            )

            Spacer(Modifier.weight(1f))

            NextButton(
                enabled = nextEnabled,
                onClick = onNextClick
            )
        }
    }
}

@Composable
private fun ProfileQuestions(
    modifier: Modifier = Modifier,
    questions: List<Question> = generateShortResponseSamples(),
    pagerState: PagerState = rememberPagerState(),
    onResponseInput: (index: Int, response: TextFieldValue) -> Unit = { _, _ -> },
    onOptionClick: (index: Int, MultipleChoiceOption) -> Unit = { _, _ -> },
    onImeActionClick: () -> Unit = {}
) {
    HorizontalPager(
        pageCount = questions.size,
        contentPadding = PaddingValues(16.dp),
        state = pagerState,
        userScrollEnabled = false,
        modifier = modifier
    ) { index ->
        when (val question = questions[index]) {
            is ShortResponse -> ShortResponseItem(
                item = question,
                onInput =  { onResponseInput(index, it) },
                onImeActionClick = onImeActionClick
            )
            is MultipleChoice -> MultipleChoiceItem(
                item = question,
                onClick = { onOptionClick(index, it) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    DatingTheme {
        OnboardingScreen(
            OnboardingState(
                generateShortResponseSamples(),
                submissionError = UiText.DynamicString("Can't be empty")
            )
        )
    }
}

@Preview
@Composable
private fun OnboardingScreenDarkPreview() {
    DatingTheme(darkTheme = true) {
        OnboardingScreen(
            OnboardingState(
                generateShortResponseSamples(),
                submissionError = UiText.DynamicString("Can't be empty")
            )
        )
    }
}