object Versions {
    object Compose {
        const val Compose = "1.2.1"
        const val ComposeCompiler = "1.3.2"
    }

    object Plugins {
        const val AGP = "8.0.0"
        const val Hilt = "2.44"
        const val Kotlin = "1.8.21"
        const val JVM = "1.6.10"
    }

    object Project {
        const val Code = 1
        const val Name = "0.1.0"
    }

    object Sdk {
        const val Compile = 33
        const val Min = 26
        const val Target = 33
    }
}

object Libs {
    object Core {
        const val Ktx = "androidx.core:core-ktx:+"
        const val LifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
    }

    object Compose {
        const val ComposeBomPlatform = "androidx.compose:compose-bom:2022.12.00"
        const val Material3 = "androidx.compose.material3:material3"
        const val IconsCore = "androidx.compose.material:material-icons-core"
        const val IconsExtended = "androidx.compose.material:material-icons-extended"
        const val WindowSizeClass = "androidx.compose.material3:material3-window-size-class"
        const val Activity = "androidx.activity:activity-compose:1.6.1"
        const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
        const val Navigation = "androidx.navigation:navigation-compose:2.5.3"
        const val Ui = "androidx.compose.ui:ui:1.4.0-alpha04"
        const val ToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val DebugToolingUi = "androidx.compose.ui:ui-tooling"
        const val LifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha01"

        const val Accompanist =
            "com.google.accompanist:accompanist-navigation-material:0.29.0-alpha"

        const val AndroidTestJunit = "androidx.compose.ui:ui-test-junit4"
        const val DebugTestManifest = "androidx.compose.ui:ui-test-manifest"
    }

    object DI {
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Plugins.Hilt}"
        const val HiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.Plugins.Hilt}"
    }

    object Test {
        const val TestJunit = "junit:junit:4.13.2"
        const val AndroidTestJunit = "androidx.test.ext:junit:1.1.5"
        const val AndroidTestEspresso = "androidx.test.espresso:espresso-core:3.5.1"
    }
}
