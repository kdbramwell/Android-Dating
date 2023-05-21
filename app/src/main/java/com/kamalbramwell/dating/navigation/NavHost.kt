package com.kamalbramwell.dating.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamalbramwell.dating.splash.SplashScreen

@Composable
fun DatingNavHost(
    navController: NavHostController,
    navBarHandler: NavBarHandler,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreen.route,
        modifier = modifier
    ) {

        composable(route = SplashScreen.route) {
            SplashScreen()
        }
    }
}

fun NavController.navigateSingleTopTo(route: String) {
    try {
        navigate(route) { launchSingleTop = true }
    } catch (e: Exception) {
        Log.d("[DatingNavHost]", "Failed to navigate to $route. ${e.message}")
    }
}