package com.kamalbramwell.dating.toast.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamalbramwell.dating.LocalToastHandler
import com.kamalbramwell.dating.toast.model.ToastData
import com.kamalbramwell.dating.toast.model.ToastType

@Composable
fun ToastOverlay(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val uiState by LocalToastHandler.current.state.collectAsStateWithLifecycle()

    Box(modifier.fillMaxSize()) {
        ToastSurface(
            uiState.toastData,
            Modifier
                .fillMaxWidth()
                .padding(
                vertical = 48.dp,
                horizontal = 24.dp)
        )
        content()
    }
}

@Composable
fun ToastSurface(
    toast: ToastData?,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = toast != null,
        modifier = modifier.zIndex(5F),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        when (toast?.type) {
            ToastType.Notice -> {
                NoticeToast(
                    title = toast.title.asString(),
                    description = toast.description.asString(),
                )
            }
            ToastType.Confirmation -> {
                ConfirmationToast(
                    title = toast.title.asString(),
                    description = toast.description.asString(),
                )
            }
            ToastType.Error -> {
                ErrorToast(
                    title = toast.title.asString(),
                    description = toast.description.asString(),
                )
            }
            else -> {}
        }
    }
}