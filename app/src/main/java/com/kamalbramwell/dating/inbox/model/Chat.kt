package com.kamalbramwell.dating.inbox.model

import com.kamalbramwell.dating.user.model.UserData

data class Chat(
    val user: UserData,
    val lastMessage: String,
    val timestamp: Long
)