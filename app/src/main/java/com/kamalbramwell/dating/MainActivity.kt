package com.kamalbramwell.dating

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.compose.rememberNavController
import com.kamalbramwell.dating.navigation.DatingBottomSheet
import com.kamalbramwell.dating.navigation.DatingNavHost
import com.kamalbramwell.dating.navigation.rememberBottomSheetNavigator
import com.kamalbramwell.dating.toast.ToastHandler
import com.kamalbramwell.dating.toast.ToastMediator
import com.kamalbramwell.dating.toast.ui.ToastOverlay
import com.kamalbramwell.dating.ui.theme.DatingTheme

val LocalToastHandler = staticCompositionLocalOf<ToastHandler> {
    error("No ToastHandler provided")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalToastHandler provides ToastMediator) {
                DatingApp()
            }
        }
    }
}

@Composable
fun DatingApp() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    DatingTheme {
        DatingBottomSheet(bottomSheetNavigator) {
            ToastOverlay {
                DatingNavHost(navController)
            }
        }
    }
}