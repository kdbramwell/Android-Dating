package com.kamalbramwell.dating.inbox.data

import com.kamalbramwell.dating.inbox.model.Chat
import com.kamalbramwell.dating.user.model.UserData

val matches = UserData.generateUsers(false).take(5)

val randomMessages = listOf(
    "I hope you love boba tea!",
    "You made my day, thanks ☺️",
    "Sure, what time?"
)
val randomChats = matches.take(3).map {
    Chat(it, randomMessages.random(), System.currentTimeMillis())
}