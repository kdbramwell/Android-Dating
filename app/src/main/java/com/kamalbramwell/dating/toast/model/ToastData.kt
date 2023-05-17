package com.kamalbramwell.dating.toast.model

import com.kamalbramwell.dating.utils.UiText

data class ToastData(
    val title: UiText = UiText.DynamicString(""),
    val description: UiText = UiText.DynamicString(""),
    val type: ToastType = ToastType.Notice
)

enum class ToastType {
    Notice, Confirmation, Error
}

enum class ToastDuration(val ms: Long) {
    Short(2_000L), Extended(3_500), Indefinite(-1L)
}

data class ToastUiState(
    val toastData: ToastData? = null,
    val isVisible: Boolean = true
)