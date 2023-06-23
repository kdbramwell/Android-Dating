package com.kamalbramwell.dating.navigation.graphs.registration

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kamalbramwell.dating.navigation.graphs.explore.Explore
import com.kamalbramwell.dating.navigation.navigateSingleTopTo
import com.kamalbramwell.dating.navigation.ui.NavBarHandler
import com.kamalbramwell.dating.registration.ui.StartScreen
import com.kamalbramwell.dating.registration.ui.auth.AuthScreen
import com.kamalbramwell.dating.registration.ui.auth.CreateAccountViewModel
import com.kamalbramwell.dating.registration.ui.auth.LoginViewModel
import com.kamalbramwell.dating.registration.ui.onboarding.OnboardingScreen

fun NavGraphBuilder.registrationGraph(
    navController: NavController,
    navBarHandler: NavBarHandler
) {
    navigation(
        startDestination = Registration.Start.route,
        route = Registration.route
    ) {
        composable(route = Registration.Start.route) {
            navBarHandler.hide()
            StartScreen(
                onEmailRegistrationClicked = navController::navigateToCreateAccount,
                onPhoneRegistrationClicked = navController::navigateToCreateAccount,
                onAlreadyRegisteredClicked = navController::navigateToLogin
            )
        }

        composable(route = Registration.Create.route) {
            AuthScreen(
                viewModel = hiltViewModel<CreateAccountViewModel>(),
                onNavigateNext = navController::navigateToOnboarding,
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

        composable(route = Registration.Onboarding.route) {
            OnboardingScreen(
                viewModel = hiltViewModel(),
                onNavigateNext = navController::navigateToHome
            )
        }
    }
}

private fun NavController.navigateToCreateAccount() {
    navigateSingleTopTo(Registration.Create.route)
}

private fun NavController.navigateToLogin() {
    navigateSingleTopTo(Registration.Login.route)
}

private fun NavController.navigateToOnboarding() {
    navigateSingleTopTo(Registration.Onboarding.route)
}

private fun NavController.navigateToHome() {
    navigateSingleTopTo(Explore.route)
}