package com.kamalbramwell.dating.navigation.graphs.inbox

import com.kamalbramwell.dating.navigation.NavDestination

enum class Inbox: NavDestination {
    Chats { override val route = "chats" },
    Messages { override val route = "messages" };

    companion object Graph: NavDestination {
        override val route = "inbox"
    }
}