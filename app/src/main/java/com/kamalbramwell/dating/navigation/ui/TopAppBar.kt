package com.kamalbramwell.dating.navigation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kamalbramwell.dating.ui.theme.defaultAppBarHeight

@Composable
fun DatingAppBar(
    modifier: Modifier = Modifier,
    left: @Composable RowScope.() -> Unit = {},
    center: @Composable RowScope.() -> Unit = {},
    right: @Composable RowScope.() -> Unit = {}
) {

    Row(
        modifier = modifier.height(defaultAppBarHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        left()
        center()
        right()
    }
}