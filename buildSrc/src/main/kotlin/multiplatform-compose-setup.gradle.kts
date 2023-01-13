plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    // TODO: iOS Support
    /*iosX64("uikitX64")
    iosArm64("uikitArm64")
    iosSimulatorArm64("uikitSimulatorArm64")*/
    jvm("desktop")
    sourceSets {
        val androidMain by named("androidMain") {
            dependencies {
                api(compose.uiTooling)
                api(compose.preview)
            }
        }
        val commonMain by named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
            }
        }
        val desktopMain by named("desktopMain") {
            dependencies {
                implementation(compose.desktop.common)
            }
        }

        // TODO: iOS Support
        /*val uikitX64Main by getting
        val uikitArm64Main by getting
        val uikitSimulatorArm64Main by getting
        val uikitMain by creating {
            dependsOn(commonMain)
            uikitX64Main.dependsOn(this)
            uikitArm64Main.dependsOn(this)
            uikitSimulatorArm64Main.dependsOn(this)
        }*/

        @Suppress("UnusedPrivateMember")
        val jvmMain by creating {
            dependsOn(commonMain)
            androidMain.dependsOn(this)
            desktopMain.dependsOn(this)
        }
    }
}
