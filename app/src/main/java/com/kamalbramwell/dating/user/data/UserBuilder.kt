package com.kamalbramwell.dating.user.data

import com.kamalbramwell.dating.onboarding.model.MultipleChoice
import com.kamalbramwell.dating.onboarding.model.MultipleChoiceOption
import com.kamalbramwell.dating.onboarding.model.Question
import com.kamalbramwell.dating.onboarding.model.ShortResponse
import com.kamalbramwell.dating.user.model.GenderOption
import com.kamalbramwell.dating.user.model.Seeking
import com.kamalbramwell.dating.user.model.UserData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserBuilder(
    private val uid: String,
    val onboardingQuestions: List<Question>,
    val onCreateUser: suspend (UserData) -> Result<Boolean>
) {

    suspend fun createUser(answeredQuestions: List<Question>): Result<Boolean> =
        if (answeredQuestions.any { !it.isAnswered }) {
            Result.failure(IncompleteException)
        } else {
            saveUser(answeredQuestions)
            Result.success(true)
        }

    private suspend fun saveUser(answeredQuestions: List<Question>) = UserData(
        uid = uid,
        name = answeredQuestions[0].asName(),
        birthday = answeredQuestions[1].asBirthday(),
        gender = answeredQuestions[2].asGender(),
        seeking = answeredQuestions[3].asSeeking(),
        photoUrl = null,
    ).run { onCreateUser(this) }

    private fun Question.asBirthday(): Long =
        try {
            LocalDate.parse(
                (this as ShortResponse).response.text,
                DateTimeFormatter.ofPattern("MM/dd/yyyy")
            ).toEpochDay()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }

    private fun Question.asGender(): GenderOption =
        GenderOption.values().first { it.id == selectedOption().id }

    private fun Question.asName(): String = (this as ShortResponse).response.text

    private fun Question.asSeeking(): Seeking =
        Seeking.values().first { it.id == selectedOption().id }

    private fun Question.selectedOption(): MultipleChoiceOption =
        (this as MultipleChoice).options.first { it.isSelected }

    companion object {
        val IncompleteException = Exception("Missing response(s) to required question(s).")
        val DateFormatException = Exception("Please enter birthday as mm/dd/yyyy.")
    }
}