package com.kamalbramwell.dating.user.data

import com.kamalbramwell.dating.registration.model.Personality
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoice
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoiceOption
import com.kamalbramwell.dating.registration.ui.onboarding.MultipleChoiceQuestion
import com.kamalbramwell.dating.registration.ui.onboarding.Question
import com.kamalbramwell.dating.registration.ui.onboarding.ShortResponse
import com.kamalbramwell.dating.registration.ui.onboarding.ShortResponseQuestion
import com.kamalbramwell.dating.utils.UiText

interface UserProfileDataSource {
    val profileQuestions: List<Question>
}

private val sampleSrQuestions = listOf(
    "What's your name?",
    "When were you born?",
)

fun generateShortResponseSamples(): List<ShortResponse> = sampleSrQuestions.map {
    ShortResponseQuestion(UiText.DynamicString(it))
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

fun generateMCSamples(): List<MultipleChoice> = sampleMcQuestions.mapIndexed { idx, question ->
    MultipleChoiceQuestion(
        prompt = UiText.DynamicString(question),
        options = sampleMcOptions[idx].map { option ->
            MultipleChoiceOption(UiText.DynamicString(option))
        },
        maxSelections = 1,
        minSelections = 1
    )
}

class DummyUserProfileDataSource(
    val shortResponse: Boolean = false,
    val multipleChoice: Boolean =  false
) : UserProfileDataSource {

    override val profileQuestions: List<Question> by lazy {
        when {
            shortResponse -> generateShortResponseSamples()
            multipleChoice -> generateMCSamples()
            else -> listOf()
        }
    }
}