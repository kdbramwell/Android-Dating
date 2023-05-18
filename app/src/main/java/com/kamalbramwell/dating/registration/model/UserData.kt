package com.kamalbramwell.dating.registration.model

data class UserData(
    val uid: String,
    val firstName: String,
    val lastName: String,
    val profilePhoto: String, // URL
    val lastSeen: Long,
    val likesYou: Boolean = false,
    val youLike: Boolean = false
)