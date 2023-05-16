package com.kamalbramwell.dating.registration.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun RegistrationScreen() {
    // Stock photos of people as bg
    Column() {

        
        EmailRegistrationButton()
        PhoneRegistrationButton()
        AlreadyRegisteredButton()
    }
}

@Composable
private fun EmailRegistrationButton() {

}

@Composable
private fun PhoneRegistrationButton() {

}

@Composable
private fun AlreadyRegisteredButton() {

}