package com.kamalbramwell.dating.user.model

import androidx.annotation.StringRes
import com.kamalbramwell.dating.R

enum class GenderOption(@StringRes val label: Int) {
    Male(R.string.gender_male),
    Female(R.string.gender_female),
    Nonbinary(R.string.gender_nonbinary)
}

enum class Seeking(@StringRes val label: Int) {
    Friends(R.string.seeking_friends),
    FWB(R.string.seeking_fwb),
    Casual(R.string.seeking_casual),
    Dating(R.string.seeking_dating),
    Relationship(R.string.seeking_ltr),
    Marriage(R.string.seeking_marriage)
}

enum class Personality {
    INTJ, INTP, ENTJ, ENTP,
    INFJ, INFP, ENFJ, ENFP,
    ISTJ, ISFJ, ESTJ, ESFJ,
    ISTP, ISFP, ESTP, ESFP
}

data class UserData(
    val uid: String,
    val name: String,
    val birthday: Long,
    val gender: GenderOption,
    val seeking: Seeking,
    val photoUrl: String? = null,
    val personality: Personality? = null,
)