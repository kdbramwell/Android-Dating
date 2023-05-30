package com.kamalbramwell.dating.explore.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kamalbramwell.dating.explore.data.accounts
import com.kamalbramwell.dating.explore.ui.components.CardStack
import com.kamalbramwell.dating.ui.theme.DatingTheme

@Composable
fun ExploreScreen() {
    val isEmpty = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isEmpty.value) {
            CardStack(
                items = accounts,
                onEmptyStack = {
                    isEmpty.value = true
                }
            )
        } else {
            Text(text = "No more cards", fontWeight = FontWeight.Bold)
        }
    }
}


@Preview
@Composable
private fun ExploreScreenPreview() {
    DatingTheme {
        ExploreScreen()
    }
}

@Preview
@Composable
private fun ExploreScreenDarkPreview() {
    DatingTheme(darkTheme = true) {
        ExploreScreen()
    }
}