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
                // Needed only for preview.
                implementation(compose.preview)

                // Koin
                val koinVersion = "3.2.2"
                val koinKspVersion = "1.0.3"
                api("io.insert-koin:koin-core:$koinVersion")
                api("io.insert-koin:koin-annotations:$koinKspVersion")
            }
            resources.srcDirs("common/src/commonMain/res")
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.finance_tracker.finance_tracker"
    }
}