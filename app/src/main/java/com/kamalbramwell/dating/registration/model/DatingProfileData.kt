package com.kamalbramwell.dating.registration.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

data class DatingProfileData(
    val bio: String = LoremIpsum(20).toString(),
    val interests: List<String>
)