package com.finance_tracker.finance_tracker.features.plans.setup

sealed interface SetupPlanAction {

    object Close: SetupPlanAction

    data class DismissDialog(val dialogKey: String): SetupPlanAction
}