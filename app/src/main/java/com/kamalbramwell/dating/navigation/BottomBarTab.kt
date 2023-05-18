package com.kamalbramwell.dating.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import com.kamalbramwell.dating.R

enum class BottomBarTab(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String,
) {
    Explore(
        R.string.explore,
        Icons.Outlined.Explore,
        ExploreGraph.route
    ),
    Matches(
        R.string.matches,
        Icons.Outlined.Favorite,
        MatchesGraph.route
    ),
    Profile(
        R.string.profile,
        Icons.Outlined.AccountCircle,
        ProfileGraph.route
    )
}