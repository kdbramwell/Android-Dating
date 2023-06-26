package com.kamalbramwell.dating.user.model

import androidx.annotation.StringRes
import com.kamalbramwell.dating.R

data class UserData(
    val uid: String = "",
    val name: String = "",
    val birthday: Long = 0,
    val gender: GenderOption = GenderOption.Error,
    val seeking: Seeking = Seeking.Error,
    val photoUrl: String = "",
    val matchQuestion: String = ""
)

enum class GenderOption(@StringRes val label: Int, val id: Int) {
    Male(R.string.gender_male, 0),
    Female(R.string.gender_female, 1),
    Nonbinary(R.string.gender_nonbinary, 2),
    Error(R.string.error_missing_property, -1)
}

enum class Seeking(@StringRes val label: Int, val id: Int) {
    Friends(R.string.seeking_friends, 0),
    FWB(R.string.seeking_fwb, 1),
    Casual(R.string.seeking_casual, 2),
    Dating(R.string.seeking_dating, 3),
    Relationship(R.string.seeking_ltr, 4),
    Marriage(R.string.seeking_marriage, 5),
    Error(R.string.error_missing_property, -1)
}