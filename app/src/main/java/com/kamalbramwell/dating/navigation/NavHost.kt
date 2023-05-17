package com.kamalbramwell.dating.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamalbramwell.dating.splash.SplashScreen

@Composable
fun DatingNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreen.route,
        modifier = modifier
    ) {

        composable(route = SplashScreen.route) {
            SplashScreen()
        }
    }
}