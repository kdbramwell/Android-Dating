package com.kamalbramwell.dating.navigation.graphs.registration

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kamalbramwell.dating.navigation.navigateSingleTopTo
import com.kamalbramwell.dating.registration.ui.auth.AuthScreen
import com.kamalbramwell.dating.registration.ui.auth.CreateAccountViewModel
import com.kamalbramwell.dating.registration.ui.auth.LoginViewModel
import com.kamalbramwell.dating.registration.ui.StartScreen

fun NavGraphBuilder.registrationGraph(navController: NavController) {
    navigation(
        startDestination = Registration.Start.route,
        route = Registration.route
    ) {

        composable(route = Registration.Start.route) {
            StartScreen(
                onEmailRegistrationClicked = navController::navigateToCreateAccount,
                onPhoneRegistrationClicked = navController::navigateToCreateAccount,
                onAlreadyRegisteredClicked = {}
            )
        }

        composable(route = Registration.Create.route) {
            AuthScreen(
                viewModel = hiltViewModel<CreateAccountViewModel>(),
                onNavigateNext = navController::navigateToHome,
                onCancelClicked = navController::navigateUp
            )
        }

        composable(route = Registration.Login.route) {
            AuthScreen(
                viewModel = hiltViewModel<LoginViewModel>(),
                onNavigateNext = navController::navigateToHome,
                onCancelClicked = navController::navigateUp
            )
        }
    }
}

private fun NavController.navigateToCreateAccount() {
    navigateSingleTopTo(Registration.Create.route)
}

private fun NavController.navigateToHome() {

}