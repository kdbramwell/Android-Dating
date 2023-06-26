package com.kamalbramwell.dating

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kamalbramwell.dating.navigation.DatingNavHost
import com.kamalbramwell.dating.navigation.ui.DatingBottomSheet
import com.kamalbramwell.dating.navigation.ui.DatingScaffold
import com.kamalbramwell.dating.navigation.ui.rememberBottomSheetNavigator
import com.kamalbramwell.dating.navigation.ui.rememberNavigationBarHandler
import com.kamalbramwell.dating.toast.ToastHandler
import com.kamalbramwell.dating.toast.ToastMediator
import com.kamalbramwell.dating.toast.ui.ToastOverlay
import com.kamalbramwell.dating.ui.theme.DatingTheme
import dagger.hilt.android.AndroidEntryPoint

val LocalToastHandler = staticCompositionLocalOf<ToastHandler> {
    error("No ToastHandler provided")
}

@AndroidEntryPoint
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
    val navBarHandler = rememberNavigationBarHandler()

    DatingTheme {
        DatingBottomSheet(bottomSheetNavigator) {
            ToastOverlay {
                DatingScaffold(navController, navBarHandler) { innerPadding ->
                    DatingNavHost(
                        navController = navController,
                        navBarHandler = navBarHandler,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(innerPadding),
                    )
                }
            }
        }
    }
}