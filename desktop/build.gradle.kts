import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(projects.common)
}

compose.desktop {
    application {
        mainClass = "com.finance_tracker.finance_tracker.MainKt"

        nativeDistributions {
            modules("java.sql")
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = rootProject.extra["appName"] as String
            packageVersion = rootProject.extra["appVersion"] as String
            copyright = "© 2023 1Coin. All rights reserved."
            vendor = "FinanceTracker"
            licenseFile.set(rootProject.file("LICENSE"))
            macOS {
                iconFile.set(project.file("src/main/resources/icons/app_icon.icns"))
            }
            windows {
                iconFile.set(project.file("src/main/resources/icons/app_icon.ico"))
            }
            linux {
                iconFile.set(project.file("src/main/resources/icons/app_icon.png"))
            }

            windows {
                menuGroup = "Desktop"
                upgradeUuid = "7abd45e8-22c0-11ec-9621-0242ac270000"
            }
        }

        buildTypes.release.proguard {
            configurationFiles.from("proguard-rules.pro")
        }
    }
}