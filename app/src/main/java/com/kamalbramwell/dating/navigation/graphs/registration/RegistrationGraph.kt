package com.kamalbramwell.dating.navigation.graphs.registration

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kamalbramwell.dating.registration.ui.RegistrationOptionsScreen

fun NavGraphBuilder.registrationGraph(navController: NavController) {
    navigation(
        startDestination = Registration.RegistrationOptions.route,
        route = Registration.route
    ) {

        composable(route = Registration.RegistrationOptions.route) {
            RegistrationOptionsScreen(
                onEmailRegistrationClicked = {},
                onPhoneRegistrationClicked = {},
                onAlreadyRegisteredClicked = {}
            )
        }
    }
}