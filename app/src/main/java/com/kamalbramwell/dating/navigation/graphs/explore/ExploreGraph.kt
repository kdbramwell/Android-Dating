package com.kamalbramwell.dating.navigation.graphs.explore

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.kamalbramwell.dating.explore.ui.ExploreScreen

fun NavGraphBuilder.exploreGraph(navController: NavController) {

    navigation(
        startDestination = Explore.Users.route,
        route = Explore.route
    ) {
        composable(route = Explore.Users.route) {
            ExploreScreen()
        }
    }
}