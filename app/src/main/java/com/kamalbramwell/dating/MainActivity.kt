package com.kamalbramwell.dating

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kamalbramwell.dating.navigation.DatingBottomSheet
import com.kamalbramwell.dating.navigation.DatingNavHost
import com.kamalbramwell.dating.navigation.rememberBottomSheetNavigator
import com.kamalbramwell.dating.ui.theme.DatingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatingApp()
        }
    }
}

@Composable
fun DatingApp() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    DatingTheme {
        DatingBottomSheet(bottomSheetNavigator) {
            DatingNavHost(navController)
        }
    }
}