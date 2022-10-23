import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        named("commonMain") {
            dependencies {
                implementation(project(":common"))

                // Odyssey
                val odysseyVersion = "1.0.0"
                implementation("io.github.alexgladkov:odyssey-core:$odysseyVersion")
                implementation("io.github.alexgladkov:odyssey-compose:$odysseyVersion")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.finance_tracker.finance_tracker.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KotlinMultiplatformComposeDesktopApplication"
            packageVersion = "1.0.0"

            windows {
                menuGroup = "Desktop"
                upgradeUuid = "7abd45e8-22c0-11ec-9621-0242ac270000"
            }
        }
    }
}