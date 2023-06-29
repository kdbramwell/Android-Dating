package com.kamalbramwell.dating.navigation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kamalbramwell.dating.ui.components.rememberBrandGradient
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.ui.theme.defaultShadowElevation
import com.kamalbramwell.dating.utils.UiText

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
        Surface(
            color =  Color.Transparent,
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .height(80.dp)
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (tab in tabs) {
                    TopLevelDestination(
                        tab = tab,
                        isSelected = selectedTab == tab,
                        onClick = {
                            selectedTab = tab
                            navigateToRoute(tab.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopLevelDestination(
    tab: BottomBarTab,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val brandGradient = rememberBrandGradient()

    Box(modifier, contentAlignment = Alignment.Center) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(46.dp)
                .shadow(if (isSelected) defaultShadowElevation else 0.dp)
                .drawBehind {
                    if (isSelected) drawRoundRect(
                        brush = brandGradient,
                        cornerRadius = CornerRadius(32f, 32f)
                    )
                }.padding(defaultContentPadding)
        ) {
            Icon(
                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                contentDescription = UiText.StringResource(tab.label).asString(),
                tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}