import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application") version Versions.Plugins.AGP apply false
    id("com.android.library") version Versions.Plugins.AGP apply false
    id("org.jetbrains.kotlin.android") version Versions.Plugins.Kotlin apply false
    id("org.jetbrains.kotlin.jvm") version Versions.Plugins.JVM apply false
    id("com.google.dagger.hilt.android") version Versions.Plugins.Hilt apply false
}

allprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}