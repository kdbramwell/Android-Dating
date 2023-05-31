package com.kamalbramwell.dating.registration.model

const val NeverJoined = 0L

data class AccountData(
    val uid: String,
    val phone: String? = null,
    val email: String? = null,
    val premiumExpiration: Long = NeverJoined,
    val signUpDate: Long = NeverJoined
)

