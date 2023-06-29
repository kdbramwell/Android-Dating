package com.kamalbramwell.dating.navigation.graphs.explore

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.kamalbramwell.dating.explore.ui.ExploreScreen
import com.kamalbramwell.dating.navigation.ui.NavBarHandler

fun NavGraphBuilder.exploreGraph(
    navController: NavController,
    navBarHandler: NavBarHandler,
    paddingValues: PaddingValues = PaddingValues()
) {

    navigation(
        startDestination = Explore.Users.route,
        route = Explore.route
    ) {
        composable(route = Explore.Users.route) {
            navBarHandler.show()
            ExploreScreen(paddingValues)
        }
    }
}