package com.kamalbramwell.dating.navigation

interface NavDestination {
    val route: String
}

object ExploreGraph : NavDestination {
    override val route: String = "explore"
}

object MatchesGraph : NavDestination {
    override val route: String = "matches"
}

object ProfileGraph : NavDestination {
    override val route: String = "profile"
}