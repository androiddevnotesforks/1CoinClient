enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "FinanceTracker"

include(
    ":common",
    ":android",
    ":desktop",
    //":ios" // TODO: iOS Support
)