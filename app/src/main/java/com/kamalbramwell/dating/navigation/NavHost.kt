package com.kamalbramwell.dating.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamalbramwell.dating.navigation.graphs.explore.Explore
import com.kamalbramwell.dating.navigation.graphs.explore.exploreGraph
import com.kamalbramwell.dating.navigation.graphs.registration.Registration
import com.kamalbramwell.dating.navigation.graphs.registration.registrationGraph
import com.kamalbramwell.dating.navigation.ui.NavBarHandler
import com.kamalbramwell.dating.navigation.ui.rememberNavigationBarHandler
import com.kamalbramwell.dating.splash.SplashScreen

@Composable
fun DatingNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    navBarHandler: NavBarHandler = rememberNavigationBarHandler(),
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {

        composable(route = "splash") {
            navBarHandler.hide()
            SplashScreen(
                viewModel = hiltViewModel(),
                onNavigateToRegistration = {
                    navController.navigateSingleTopTo(Registration.route)
                },
                onNavigateToHome = {
                    navController.navigateSingleTopTo(Explore.route)
                }
            )
        }

        registrationGraph(navController, navBarHandler)
        exploreGraph(navController, navBarHandler)
    }
}

fun NavController.navigateSingleTopTo(route: String) {
    try {
        navigate(route) { launchSingleTop = true }
    } catch (e: Exception) {
        Log.d("[DatingNavHost]", "Failed to navigate to $route. ${e.message}")
    }
}