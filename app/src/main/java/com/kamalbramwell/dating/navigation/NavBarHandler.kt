package com.kamalbramwell.dating.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

interface NavBarHandler {
    val isVisible: State<Boolean>
    fun show()
    fun hide()
}

private class NavBarMediator(initialVisibility: Boolean = false): NavBarHandler {
    override var isVisible = mutableStateOf(initialVisibility)

    override fun show() {
        isVisible.value = true
    }

    override fun hide() {
        isVisible.value = false
    }
}

@Composable
fun rememberNavigationBarHandler(initialVisibility: Boolean = false): NavBarHandler =
    remember { NavBarMediator(initialVisibility) }