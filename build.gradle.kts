// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    repositories {
        google()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.13")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.1")
    }
}

plugins {
    id("com.android.application") version "7.3.0-rc01" apply false
    id("com.android.library") version "7.3.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
}