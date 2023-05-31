package com.kamalbramwell.dating.navigation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun DatingNavigationBar(
    tabs: Array<BottomBarTab>,
    modifier: Modifier = Modifier,
    navBarHandler: NavBarHandler = rememberNavigationBarHandler(),
    navigateToRoute: (String) -> Unit = {},
) {
    var selectedTab by remember { mutableStateOf(tabs.first()) }
    val isVisible by navBarHandler.isVisible

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        val containerColor = MaterialTheme.colorScheme.primary
        NavigationBar(
            modifier = modifier,
            containerColor = containerColor
        ) {
            val selectedColor = MaterialTheme.colorScheme.onPrimary
            val unselectedColor = MaterialTheme.colorScheme.outline

            for (tab in tabs) {
                NavigationBarItem(
                    selected = selectedTab == tab,
                    onClick = {
                        selectedTab = tab
                        navigateToRoute(tab.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = containerColor,
                        selectedIconColor = selectedColor,
                        unselectedIconColor = unselectedColor,
                        selectedTextColor = selectedColor,
                        unselectedTextColor = unselectedColor
                    ),
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = stringResource(tab.label),
                        )
                    }
                )
            }
        }
    }
}