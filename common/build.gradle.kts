plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

kotlin {
    android()
    jvm("desktop")
    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
                api(compose.uiTooling)
                // Needed only for preview.
                api(compose.preview)

                // Koin
                val koinVersion = "3.2.2"
                api("io.insert-koin:koin-core:$koinVersion")

                // Odyssey
                val odysseyVersion = "1.0.0"
                implementation("io.github.alexgladkov:odyssey-core:$odysseyVersion")
                implementation("io.github.alexgladkov:odyssey-compose:$odysseyVersion")
            }
        }
        named("desktopMain") {
            dependencies {
                api(compose.uiTooling)
                // Needed only for preview.
                api(compose.preview)

                // Koin
                val koinVersion = "3.2.2"
                implementation("io.insert-koin:koin-core:$koinVersion")

                // SQL Delight
                val sqlDelightVersion = "1.5.4"
                implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
            }
        }
        named("androidMain") {
            dependencies {
                api("androidx.appcompat:appcompat:1.5.1")
                api("androidx.core:core-ktx:1.9.0")

                // ViewModel
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

                // Koin
                val koinVersion = "3.2.2"
                val koinAndroidComposeVersion = "3.2.1"
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion")

                // SQL Delight
                val sqlDelightVersion = "1.5.4"
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
    }
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res", "src/commonMain/resources")
        }
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.finance_tracker.finance_tracker"
    }
}