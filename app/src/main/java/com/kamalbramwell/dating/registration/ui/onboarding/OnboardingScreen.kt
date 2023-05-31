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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
        viewModel::onComplete
    )
}

@Composable
fun OnboardingScreen(
    uiState: OnboardingState = OnboardingState(),
    onResponse: (index: Int, value: TextFieldValue) -> Unit = { _, _ -> },
    onNavigateNext: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val backEnabled by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    val completed by remember {
        derivedStateOf { listState.firstVisibleItemIndex >= uiState.questions.size }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(defaultContentPadding)
            .semantics { testTag = OnboardingTestTag },
    ) {
        Heading(text = UiText.StringResource(R.string.onboarding_heading))

        QuestionCards(
            modifier = Modifier.weight(1f),
            scrollState = listState,
            onResponse = onResponse
        )

        Row {
            BackButton(
                modifier = Modifier.padding(8.dp),
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
                modifier = Modifier.padding(8.dp),
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
private fun QuestionCards(
    modifier: Modifier = Modifier,
    questions: List<ProfileQuestion> = generateSamples(),
    scrollState: LazyListState = rememberLazyListState(),
    onResponse: (index: Int, response: TextFieldValue) -> Unit = { _, _ -> }
) {
    LazyRow(
        modifier = modifier,
        state = scrollState,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(items = questions) { index, question ->
            QuestionItem(
                modifier = Modifier.fillParentMaxWidth(1f),
                item = question,
                onInput = { response ->
                    onResponse(index, response)
                }
            )
        }
    }
}

@Composable
private fun QuestionItem(
    item: ProfileQuestion,
    modifier: Modifier = Modifier,
    onInput: (TextFieldValue) -> Unit = {}
) {
    var response by remember { mutableStateOf(item.response) }

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(32.dp),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            verticalArrangement = Arrangement.Center
        ) {
            DatingText(
                text = item.prompt,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            InputField(
                textFieldValue = response,
                onTextChanged = {
                    onInput(it)
                    response = it
                },
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