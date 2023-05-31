package com.kamalbramwell.dating.toast

import com.kamalbramwell.dating.toast.model.ToastData
import com.kamalbramwell.dating.toast.model.ToastDuration
import com.kamalbramwell.dating.toast.model.ToastUiState
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface ToastHandler {
    val state: StateFlow<ToastUiState>
    fun show(toast: ToastData, duration: ToastDuration = ToastDuration.Short)
    fun dismissAll()
}

private val dummyToast = ToastData(
    UiText.DynamicString("Hello World!"),
    UiText.DynamicString("Looking good")
)

object ToastMediator : ToastHandler {
    private val scope = CoroutineScope(
        CoroutineName("Toasts") + Job() + Dispatchers.Main
    )
    private var currentToast: Job? = null

    private val _state = MutableStateFlow(ToastUiState())
    override val state: StateFlow<ToastUiState> = _state

    override fun show(toast: ToastData, duration: ToastDuration) {
        scope.launch {
            when (duration) {
                ToastDuration.Short -> showThenDismiss(toast, duration)
                ToastDuration.Extended -> showThenDismiss(toast, duration)
                ToastDuration.Indefinite -> showIndefiniteToast(toast)
            }
        }
    }

    private fun showThenDismiss(toast: ToastData, duration: ToastDuration) {
        scope.launch {
            // Allow the current toast to display for its full duration...
            currentToast?.join()

            // ... then show this one.
            currentToast = scope.launch {
                _state.update { it.copy(toastData =  toast, isVisible = true) }
                delay(duration.ms)
                dismiss()
            }
        }
    }

    private fun dismiss() {
        scope.launch {
            // Notify UI so toast exit animation can be shown
            _state.update { it.copy(isVisible = false) }
            // Give the animation time before clearing the data backing the toast
            delay(1_000)
            _state.update { ToastUiState() }
        }
    }

    private fun showIndefiniteToast(toast: ToastData) {
        scope.launch {
            // Cancel current toasts...
            currentToast?.cancel()

            // Update the job to this so ephemeral toasts may queue themselves for display.
            currentToast = scope.launch {
                _state.update { it.copy(toastData =  toast) }
            }
        }
    }

    override fun dismissAll() {
        currentToast?.cancel()
        dismiss()
    }
}