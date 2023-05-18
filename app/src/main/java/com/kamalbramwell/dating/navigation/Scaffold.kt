package com.kamalbramwell.dating.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun DatingScaffold(navController: NavHostController,
                   navBarHandler: NavBarHandler,
                   modifier: Modifier = Modifier,
                   content: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = {
            DatingNavigationBar(
                tabs = BottomBarTab.values(),
                navBarHandler = navBarHandler,
                navigateToRoute = { navController.navigateSingleTopTo(it) }
            )
        },
        content = content
    )
}