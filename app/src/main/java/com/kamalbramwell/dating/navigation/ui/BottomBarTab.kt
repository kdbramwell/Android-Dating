package com.kamalbramwell.dating.navigation.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.navigation.graphs.explore.Explore as ExploreGraph
import com.kamalbramwell.dating.navigation.graphs.inbox.Inbox as InboxGraph


enum class BottomBarTab(
    @StringRes val label: Int,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String,
) {
    Explore(
        R.string.explore,
        Icons.Outlined.Search,
        Icons.Filled.Search,
        ExploreGraph.route
    ),
    Inbox(
        R.string.matches,
        Icons.Outlined.ChatBubbleOutline,
        Icons.Filled.ChatBubble,
        InboxGraph.route
    ),
    Profile(
        R.string.profile,
        Icons.Outlined.AccountCircle,
        Icons.Filled.AccountCircle,
        ExploreGraph.route
    )
}