package com.kamalbramwell.dating.registration.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.kamalbramwell.dating.registration.ui.components.MultipleChoiceItem
import com.kamalbramwell.dating.registration.ui.components.ShortResponseItem
import com.kamalbramwell.dating.ui.components.BackButton
import com.kamalbramwell.dating.ui.components.NextButton
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.launch

const val OnboardingTestTag = "Onboarding"

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = viewModel(),
    onNavigateNext: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OnboardingScreen(
        uiState,
        viewModel::onResponse,
        viewModel::onChoiceClicked,
        viewModel::onComplete
    )

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) onNavigateNext()
    }
}

@Composable
fun OnboardingScreen(
    uiState: OnboardingState = OnboardingState(),
    onResponse: (index: Int, value: TextFieldValue) -> Unit = { _, _ -> },
    onOptionClick: (index: Int, option: MultipleChoiceOption) -> Unit = { _, _ -> },
    onNavigateNext: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val backEnabled by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    val completed by remember {
        derivedStateOf { listState.firstVisibleItemIndex >= uiState.questions.size -1 }
    }

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
            scrollState = listState,
            onResponseInput = onResponse,
            onOptionClick = onOptionClick
        )

        Row {
            BackButton(
                enabled = backEnabled,
                onClick = {
                      coroutineScope.launch {
                          val currentIndex = listState.firstVisibleItemIndex
                          listState.animateScrollToItem(currentIndex - 1)
                      }
                },
            )

            Spacer(Modifier.weight(1f))

            NextButton(
                enabled = uiState.nextEnabled,
                onClick = {
                    coroutineScope.launch {
                        val currentIndex = listState.firstVisibleItemIndex
                        if (completed) {
                            onNavigateNext()
                        } else {
                            listState.animateScrollToItem(currentIndex + 1)
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun ProfileQuestions(
    modifier: Modifier = Modifier,
    questions: List<Question> = generateSamples(),
    scrollState: LazyListState = rememberLazyListState(),
    onResponseInput: (index: Int, response: TextFieldValue) -> Unit = { _, _ -> },
    onOptionClick: (index: Int, MultipleChoiceOption) -> Unit = { _, _ -> }
) {
    LazyRow(
        modifier = modifier,
        state = scrollState,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(64.dp),
        verticalAlignment = Alignment.CenterVertically,
        userScrollEnabled = false
    ) {
        itemsIndexed(items = questions) { index, question ->
            when (question) {
                is ShortResponse -> ShortResponseItem(
                    item = question,
                    onInput =  { onResponseInput(index, it) }
                )
                is MultipleChoice -> MultipleChoiceItem(
                    item = question,
                    onClick = { onOptionClick(index, it) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    DatingTheme {
        OnboardingScreen(
            OnboardingState(generateSamples())
        )
    }
}

@Preview
@Composable
private fun OnboardingScreenDarkPreview() {
    DatingTheme(darkTheme = true) {
        OnboardingScreen(
            OnboardingState(generateSamples())
        )
    }
}