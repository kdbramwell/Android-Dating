package com.kamalbramwell.dating.registration.ui.onboarding

import androidx.lifecycle.ViewModel
import com.kamalbramwell.dating.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class OnboardingState(
    private val questions: List<ProfileQuestion> = listOf(),
    private val navigateToHome: Boolean = false,
)

@JvmInline
value class ProfileQuestion(val text: UiText)

class OnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingState())
    val uiState = _uiState.asStateFlow()


}