package com.kamalbramwell.dating.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamalbramwell.dating.explore.ui.components.LikeButton
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultShadowElevation
import com.kamalbramwell.dating.ui.theme.lightOrange
import com.kamalbramwell.dating.ui.theme.magenta
import kotlin.random.Random

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
private fun Background(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTag = SplashScreenTestTag },
        contentAlignment = Alignment.Center
    ) {
        var currentRotation by remember { mutableStateOf(Random.nextFloat() * 360f) }
        val rotation = remember { Animatable(currentRotation) }

        LaunchedEffect(true) {
            rotation.animateTo(
                targetValue = currentRotation + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotation = value
            }
        }
        LikeButton(
            modifier
                .rotate(currentRotation)
                .size(64.dp)
                .shadow(
                    shape = CircleShape,
                    elevation = defaultShadowElevation,
                    ambientColor = lightOrange,
                    spotColor = magenta
                )
        )
    }
}

@Preview
@Composable
private fun WallpaperPreview() {
    DatingTheme {
        SplashScreen(SplashScreenState())
    }
}