package com.kamalbramwell.dating.navigation.graphs.registration

import com.kamalbramwell.dating.navigation.NavDestination

enum class Registration : NavDestination {
    Start { override val route = "registration_options" },
    Create { override val route = "create" },
    Login { override val route = "login" },
    ForgotPassword { override val route = "reset" };

    companion object Graph : NavDestination {
        override val route = "registration"
    }
}