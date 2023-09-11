import java.io.FileInputStream
import java.util.Properties

plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    id("com.google.firebase.crashlytics")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.gms.google-services")
    kotlin("android")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("../1coin-debug.keystore")
            storePassword = "00000000"
            keyPassword = "00000000"
            keyAlias = "1coin"
        }
        create("release") {
            storeFile = file("../1coin.keystore")
            storePassword = keystoreProperties["storePassword"] as? String
            keyPassword = keystoreProperties["keyPassword"] as? String
            keyAlias = keystoreProperties["keyAlias"] as? String
        }
    }
    namespace = "com.finance_tracker.finance_tracker"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.finance_tracker.finance_tracker"
        minSdk = 26
        targetSdk = 33
        versionCode = 11
        versionName = rootProject.extra["appVersion"] as String
        signingConfig = signingConfigs.getByName("debug")
        val appName = rootProject.extra["appName"] as String
        resValue("string", "app_name", appName)
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        create("staging") {
            initWith(getByName("release"))
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
}

dependencies {
    implementation(projects.common)

    debugImplementation(libs.leakcanary)

    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    implementation(libs.bundles.koin.android)
}