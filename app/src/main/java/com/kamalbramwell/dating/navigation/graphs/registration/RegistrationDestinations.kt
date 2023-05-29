package com.kamalbramwell.dating.navigation.graphs.registration

import com.kamalbramwell.dating.navigation.NavDestination

enum class Registration : NavDestination {
    RegistrationOptions { override val route = "registration" },
    EmailRegistration { override val route = "email" },
    PhoneRegistration { override val route = "phone" },
    AccountLogin { override val route = "login" },
    ForgotPassword { override val route = "resetPassword" };

    companion object Graph : NavDestination {
        override val route = "registration"
    }
}