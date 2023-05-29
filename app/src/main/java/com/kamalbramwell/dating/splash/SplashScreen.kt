package com.kamalbramwell.dating.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamalbramwell.dating.registration.data.StockImageDataSource
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
    when {
        uiState.navigateToHome -> onNavigateToHome()
        uiState.navigateToRegistration -> onNavigateToRegistration()
    }
}

const val SplashScreenTestTag = "SplashScreen"

@Composable
private fun Background() {
    val resourceId = remember { StockImageDataSource.random() }
    Image(
        painter = painterResource(id = resourceId),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTag = SplashScreenTestTag },
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun WallpaperPreview() {
    DatingTheme {
        SplashScreen(SplashScreenState())
    }
}