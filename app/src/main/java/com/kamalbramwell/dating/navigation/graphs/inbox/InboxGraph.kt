package com.kamalbramwell.dating.navigation.graphs.inbox

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.kamalbramwell.dating.inbox.ui.InboxScreen
import com.kamalbramwell.dating.navigation.ui.NavBarHandler

fun NavGraphBuilder.inboxGraph(
    navController: NavController,
    navBarHandler: NavBarHandler,
    paddingValues: PaddingValues = PaddingValues()
) {

    navigation(
        startDestination = Inbox.Chats.route,
        route = Inbox.route
    ) {
        composable(route = Inbox.Chats.route) {
            navBarHandler.show()
            InboxScreen(paddingValues)
        }
    }
}