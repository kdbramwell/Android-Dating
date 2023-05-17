package com.kamalbramwell.dating.navigation

interface NavDestination {
    val route: String
}

object SplashScreen : NavDestination {
    override val route: String = "splash"
}