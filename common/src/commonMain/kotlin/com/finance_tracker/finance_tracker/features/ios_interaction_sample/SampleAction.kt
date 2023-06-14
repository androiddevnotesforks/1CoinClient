package com.finance_tracker.finance_tracker.features.ios_interaction_sample

sealed class SampleAction {
    object NavigateBack: SampleAction()
    data class OpenDetailsScreen(val navScreenParams: NavScreenParams): SampleAction()
}
