plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("com.google.devtools.ksp") version "1.7.20-1.0.6"
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
                val koinKspVersion = "1.0.3"
                api("io.insert-koin:koin-core:$koinVersion")
                api("io.insert-koin:koin-annotations:$koinKspVersion")

                // ViewModel
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
            }
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
        named("desktopMain") {
            dependencies {
                api(compose.uiTooling)
                // Needed only for preview.
                api(compose.preview)
            }
        }
        named("androidMain") {
            dependencies {
                api("androidx.appcompat:appcompat:1.5.1")
                api("androidx.core:core-ktx:1.9.0")
            }
        }
    }
}

dependencies {
    val koinKspVersion = "1.0.3"
    add("kspCommonMainMetadata", "io.insert-koin:koin-ksp-compiler:$koinKspVersion")
}

tasks.preBuild {
    dependsOn(":common:kspCommonMainKotlinMetadata")
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