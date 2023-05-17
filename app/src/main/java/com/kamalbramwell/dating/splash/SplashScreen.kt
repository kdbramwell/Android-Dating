package com.kamalbramwell.dating.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamalbramwell.dating.registration.data.StockImageDataSource
import com.kamalbramwell.dating.ui.theme.DatingTheme

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateToRegistration: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Wallpaper()

    when {
        uiState.navigateToHome -> onNavigateToHome()
        uiState.navigateToRegistration -> onNavigateToRegistration()
    }
}

@Composable
private fun Wallpaper() {
    Image(
        painter = painterResource(id = StockImageDataSource.random()),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun WallpaperPreview() {
    DatingTheme {
        Wallpaper()
    }
}