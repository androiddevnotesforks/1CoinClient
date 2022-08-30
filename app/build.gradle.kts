plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.finance_tracker.finance_tracker"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.finance_tracker.finance_tracker"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
}

dependencies {

    val composeVersion = "1.2.1"
    //Jetpack Compose
    implementation("androidx.compose.ui:ui:${composeVersion}")
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.5.1")
    // Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.5.1")
    // Animations
    implementation("androidx.compose.animation:animation:${composeVersion}")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:${composeVersion}")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:${composeVersion}")
    // Material Design
    implementation("androidx.compose.material:material:${composeVersion}")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:${composeVersion}")
    // Integration with observables
    implementation("androidx.compose.runtime:runtime-livedata:${composeVersion}")

    //Chucker
    val chukerVersion = "3.5.2"
    debugImplementation("com.github.chuckerteam.chucker:library:${chukerVersion}")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:${chukerVersion}")

    //Core
    implementation("androidx.core:core-ktx:1.8.0")

    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    //View
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // UI Tests
    // androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
}