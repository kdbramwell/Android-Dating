package com.kamalbramwell.dating.user.data

import androidx.compose.ui.text.input.TextFieldValue
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.onboarding.model.MultipleChoice
import com.kamalbramwell.dating.onboarding.model.MultipleChoiceOption
import com.kamalbramwell.dating.onboarding.model.MultipleChoiceQuestion
import com.kamalbramwell.dating.onboarding.model.Question
import com.kamalbramwell.dating.onboarding.model.ShortResponse
import com.kamalbramwell.dating.onboarding.model.ShortResponseQuestion
import com.kamalbramwell.dating.user.model.GenderOption
import com.kamalbramwell.dating.user.model.Seeking
import com.kamalbramwell.dating.user.model.UserData
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface UserProfileDataSource {
    val onboardingQuestions: List<Question>

    fun newUserFromOnboarding(): UserBuilder
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
)

fun generateMCSamples(answered: Boolean = false): List<MultipleChoice> =
    sampleMcQuestionsAndOptions.map {
        MultipleChoiceQuestion(
            prompt = UiText.StringResource(it.first),
            options = it.second.mapIndexed { opIdx, option ->
                MultipleChoiceOption(
                    id = opIdx,
                    label = option,
                    isSelected = opIdx == 0 && answered
                )
            },
            maxSelections = 1,
            minSelections = 1
        )
    }

fun generateProfileQuestions(answered: Boolean = false): List<Question> =
    generateShortResponseSamples(answered) + generateMCSamples(answered)

class DummyUserProfileDataSource(
    private val shortResponse: Boolean = false,
    private val multipleChoice: Boolean =  false,
    private val both: Boolean = false,
    private val override: List<Question>? = null,
    private val answered: Boolean = false,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserProfileDataSource {

    private var user: UserData? = null

    override val onboardingQuestions: List<Question> by lazy {
        when {
            override != null -> override
            both -> generateProfileQuestions(answered)
            shortResponse -> generateShortResponseSamples(answered)
            multipleChoice -> generateMCSamples(answered)
            else -> listOf()
        }
    }

    override fun newUserFromOnboarding(): UserBuilder {
        return UserBuilder(
            uid = "",
            onboardingQuestions = onboardingQuestions,
            onCreateUser = {
                user = it
                Result.success(true)
            }
        )
    }
}