package com.kamalbramwell.dating.auth.model

data class AccountData(
    val uid: String,
    val email: String? = null,
    val phone: String? = null
)

