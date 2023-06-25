package com.kamalbramwell.dating.user.data

import com.kamalbramwell.dating.onboarding.model.Question
import com.kamalbramwell.dating.onboarding.model.ShortResponse
import com.kamalbramwell.dating.user.model.GenderOption
import com.kamalbramwell.dating.user.model.Seeking
import com.kamalbramwell.dating.user.model.UserData
import java.util.Date

class UserBuilder(
    private val uid: String,
    val onboardingQuestions: List<Question>,
    val onCreateUser: (UserData) -> Result<Boolean>
) {

    fun createUser(answeredQuestions: List<Question>): Result<Boolean> =
        if (answeredQuestions.any { !it.isAnswered }) {
            Result.failure(IncompleteException)
        } else {
            saveUser("", answeredQuestions)
            Result.success(true)
        }

    private fun saveUser(
        uid: String,
        answeredQuestions: List<Question>
    ) = UserData(
        uid = uid,
        name = answeredQuestions[0].asName(),
        birthday = answeredQuestions[1].asBirthday(),
        gender = answeredQuestions[2].asGender(),
        seeking = answeredQuestions[3].asSeeking(),
        photoUrl = null,
    ).run { onCreateUser(this) }

    private fun Question.asBirthday(): Date = Date(0)
    private fun Question.asGender(): GenderOption = GenderOption.Nonbinary
    private fun Question.asName(): String = (this as ShortResponse).response.text
    private fun Question.asSeeking(): Seeking = Seeking.FWB

    companion object {
        val IncompleteException = Exception("Missing response(s) to required question(s).")
    }
}