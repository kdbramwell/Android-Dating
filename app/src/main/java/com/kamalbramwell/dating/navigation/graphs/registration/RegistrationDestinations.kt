package com.kamalbramwell.dating.navigation.graphs.registration

import com.kamalbramwell.dating.navigation.NavDestination

object RegistrationGraph: NavDestination {
    override val route: String = "registration"
}

object RegistrationOptions : NavDestination {
    override val route: String = "registration"
}

object EmailRegistration : NavDestination {
    override val route: String = "email"
}

object PhoneRegistration : NavDestination {
    override val route: String = "phone"
}

object AccountLogin : NavDestination {
    override val route: String = "login"
}

object ForgotPassword: NavDestination {
    override val route: String = "resetPassword"
}