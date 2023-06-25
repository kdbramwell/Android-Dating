package com.kamalbramwell.dating.user.data

import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.onboarding.model.MultipleChoice
import com.kamalbramwell.dating.onboarding.model.MultipleChoiceOption
import com.kamalbramwell.dating.onboarding.model.Question
import com.kamalbramwell.dating.onboarding.model.ShortResponse
import com.kamalbramwell.dating.onboarding.ui.MultipleChoiceQuestion
import com.kamalbramwell.dating.onboarding.ui.ShortResponseQuestion
import com.kamalbramwell.dating.user.data.UserProfileDataSource.Companion.IncompleteException
import com.kamalbramwell.dating.user.model.GenderOption
import com.kamalbramwell.dating.user.model.Personality
import com.kamalbramwell.dating.user.model.Seeking
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

private val sampleSrQuestionsAndHints = listOf(
    R.string.onboarding_q_name to "John Smith",
    R.string.onboarding_q_birthday to "1/1/1990"
)

fun generateShortResponseSamples(answered: Boolean = false): List<ShortResponse> =
    sampleSrQuestionsAndHints.map {
        ShortResponseQuestion(
            prompt = UiText.StringResource(it.first),
            response = if (answered) TextFieldValue("helloworld") else TextFieldValue(),
            hint = UiText.DynamicString(it.second)
        )
    }

private val sampleMcQuestionsAndOptions = listOf(
    R.string.onboarding_q_gender to GenderOption.values().map { UiText.StringResource(it.label) },
    R.string.onboarding_q_seeking to Seeking.values().map { UiText.StringResource(it.label) },
    R.string.onboarding_q_mbti to Personality.values()
        .map { UiText.DynamicString("$it ✨") }
            + UiText.StringResource(R.string.mbti_not_sure)
)

fun generateMCSamples(answered: Boolean = false): List<MultipleChoice> = sampleMcQuestionsAndOptions.map {
    MultipleChoiceQuestion(
        prompt = UiText.StringResource(it.first),
        options = it.second.mapIndexed { opIdx, option ->
            MultipleChoiceOption(
                label = option,
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
    private val override: List<Question>? = null,
    private val answered: Boolean = false,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserProfileDataSource {

    // TODO: In-memory caching of user details received during registration and onboarding

    override val profileQuestions: List<Question> by lazy {
        when {
            override != null -> override
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