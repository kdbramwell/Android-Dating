package com.kamalbramwell.dating.navigation.graphs.explore

import com.kamalbramwell.dating.navigation.NavDestination

enum class Explore : NavDestination {
    Users { override val route = "users" };

    companion object Graph: NavDestination {
        override val route = "explore"
    }
}