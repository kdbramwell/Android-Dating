package com.kamalbramwell.dating.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamalbramwell.dating.ui.components.rememberBrandGradient
import com.kamalbramwell.dating.ui.theme.DatingTheme

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel = viewModel(),
    onNavigateToRegistration: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SplashScreen(uiState, onNavigateToRegistration, onNavigateToHome)
}

@Composable
fun SplashScreen(
    uiState: SplashScreenState = SplashScreenState(),
    onNavigateToRegistration: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    Background()

    LaunchedEffect(uiState.navigateToHome, uiState.navigateToRegistration) {
        when {
            uiState.navigateToHome -> onNavigateToHome()
            uiState.navigateToRegistration -> onNavigateToRegistration()
        }
    }
}

const val SplashScreenTestTag = "SplashScreen"

@Composable
private fun Background() {
    val brandGradient = rememberBrandGradient()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brandGradient)
            .semantics { testTag = SplashScreenTestTag },
    )
}

@Preview
@Composable
private fun WallpaperPreview() {
    DatingTheme {
        SplashScreen(SplashScreenState())
    }
}