enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "FinanceTracker"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}