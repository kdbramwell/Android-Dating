package com.kamalbramwell.dating.navigation.graphs.registration

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kamalbramwell.dating.navigation.navigateSingleTopTo
import com.kamalbramwell.dating.registration.ui.CreateAccountScreen
import com.kamalbramwell.dating.registration.ui.RegistrationOptionsScreen

fun NavGraphBuilder.registrationGraph(navController: NavController) {
    navigation(
        startDestination = Registration.Start.route,
        route = Registration.route
    ) {

        composable(route = Registration.Start.route) {
            RegistrationOptionsScreen(
                onEmailRegistrationClicked = navController::navigateToCreateAccount,
                onPhoneRegistrationClicked = navController::navigateToCreateAccount,
                onAlreadyRegisteredClicked = {}
            )
        }

        composable(route = Registration.Create.route) {
            CreateAccountScreen(
                viewModel = hiltViewModel(),
                onNavigateNext = {},
                onCancelClicked = navController::navigateUp
            )
        }
    }
}

private fun NavController.navigateToCreateAccount() {
    navigateSingleTopTo(Registration.Create.route)
}