package com.kamalbramwell.dating.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamalbramwell.dating.registration.ui.RegistrationOptionsScreen
import com.kamalbramwell.dating.splash.SplashScreen
import com.kamalbramwell.dating.splash.SplashScreenViewModel

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
            SplashScreen(
                viewModel = viewModel(factory = SplashScreenViewModel.Factory),
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