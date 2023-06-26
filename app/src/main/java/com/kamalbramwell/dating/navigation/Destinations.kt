package com.kamalbramwell.dating.navigation

interface NavDestination {
    val route: String
}

object ProfileGraph : NavDestination {
    override val route: String = "profile"
}