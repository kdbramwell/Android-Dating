package com.kamalbramwell.dating.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamalbramwell.dating.registration.ui.RegistrationOptionsScreen
import com.kamalbramwell.dating.splash.SplashScreen

@Composable
fun DatingNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    navBarHandler: NavBarHandler = rememberNavigationBarHandler(),
    startDestination: String = SplashScreen.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(route = SplashScreen.route) {
            SplashScreen(
                viewModel = hiltViewModel(),
                onNavigateToRegistration = {
                    navController.navigateSingleTopTo(Registration.route)
                }
            )
        }

        composable(route = Registration.route) {
            RegistrationOptionsScreen(
                onEmailRegistrationClicked = {},
                onPhoneRegistrationClicked = {},
                onAlreadyRegisteredClicked = {}
            )
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