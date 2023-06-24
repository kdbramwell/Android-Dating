package com.kamalbramwell.dating.user.model

enum class GenderOption {
    Male, Female, Nonbinary
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
    val gender: GenderOption,
    val personality: Personality,
    val city: String,
    val photoUrl: String,
    val likesYou: Boolean = false,
    val youLike: Boolean = false
)