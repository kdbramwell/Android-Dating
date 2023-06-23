package com.kamalbramwell.dating.user.data

import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.registration.model.Personality
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoice
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoiceOption
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoiceQuestion
import com.kamalbramwell.dating.registration.ui.onboarding.Question
import com.kamalbramwell.dating.registration.ui.onboarding.ShortResponse
import com.kamalbramwell.dating.registration.ui.onboarding.ShortResponseQuestion
import com.kamalbramwell.dating.user.data.UserProfileDataSource.Companion.IncompleteException
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserProfileDataSource {
    val profileQuestions: List<Question>

    suspend fun submit(answeredQuestions: List<Question>): Result<Boolean>

    companion object {
        val IncompleteException = Exception("Missing response(s) to required question(s).")
    }
}

private val sampleSrQuestions = listOf(
    "What's your name?",
    "When were you born?",
)

fun generateShortResponseSamples(answered: Boolean = false): List<ShortResponse> = sampleSrQuestions.map {
    ShortResponseQuestion(
        prompt = UiText.DynamicString(it),
        response = if (answered) TextFieldValue("helloworld") else TextFieldValue()
    )
}

private val sampleMcQuestions = listOf(
    "How do you identify",
    "What are you looking for",
    "Whats your personality type",
)

private val sampleMcOptions = listOf(
    listOf("Male", "Female", "Non Binary"),
    listOf(
        "Friends",
        "FWB",
        "Something casual",
        "Exclusive dating",
        "Long term relationship",
        "Wedding bands"
    ),
    Personality.values().map { it.toString() }
)

fun generateMCSamples(answered: Boolean = false): List<MultipleChoice> = sampleMcQuestions.mapIndexed { idx, question ->
    MultipleChoiceQuestion(
        prompt = UiText.DynamicString(question),
        options = sampleMcOptions[idx].mapIndexed { opIdx, option ->
            MultipleChoiceOption(
                label = UiText.DynamicString(option),
                isSelected = opIdx == 0 && answered
            )
        },
        maxSelections = 1,
        minSelections = 1
    )
}

class DummyUserProfileDataSource(
    private val shortResponse: Boolean = false,
    private val multipleChoice: Boolean =  false,
    private val both: Boolean = false,
    private val answered: Boolean = false,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserProfileDataSource {

    override val profileQuestions: List<Question> by lazy {
        when {
            both -> generateShortResponseSamples(answered) + generateMCSamples(answered)
            shortResponse -> generateShortResponseSamples(answered)
            multipleChoice -> generateMCSamples(answered)
            else -> listOf()
        }
    }

    override suspend fun submit(answeredQuestions: List<Question>): Result<Boolean> =
        withContext(dispatcher) {
            if (answeredQuestions.any { !it.isAnswered }) Result.failure(IncompleteException)
            else Result.success(true)
        }
}