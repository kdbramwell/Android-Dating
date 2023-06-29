package com.kamalbramwell.dating.explore.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kamalbramwell.dating.LocalToastHandler
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.explore.data.accounts
import com.kamalbramwell.dating.explore.ui.components.CardStack
import com.kamalbramwell.dating.navigation.ui.DatingAppBar
import com.kamalbramwell.dating.toast.model.ToastData
import com.kamalbramwell.dating.toast.model.ToastDuration
import com.kamalbramwell.dating.toast.model.ToastType
import com.kamalbramwell.dating.ui.components.AppBarTitle
import com.kamalbramwell.dating.ui.components.DatingText
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.utils.UiText

@Composable
fun ExploreScreen(paddingValues: PaddingValues = PaddingValues()) {
    val toastHandler = LocalToastHandler.current

    LaunchedEffect(true) {
        toastHandler.show(
            ToastData(
                UiText.DynamicString("Complete your profile!"),
                UiText.DynamicString("You won\'t be able to find matches until you do."),
                ToastType.Error
            ),
            ToastDuration.Extended
        )
    }
    val nearbyUsers = accounts
    var isEmpty by remember { mutableStateOf(nearbyUsers.isEmpty()) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DatingAppBar(
                center = { AppBarTitle(text = UiText.StringResource(R.string.explore)) }
            )

            if (!isEmpty) {
                CardStack(
                    items = nearbyUsers,
                    onEmptyStack = { isEmpty = true },
                    modifier = Modifier.padding(defaultContentPadding)
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DatingText(
                        text = UiText.DynamicString("No nearby users found."),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
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