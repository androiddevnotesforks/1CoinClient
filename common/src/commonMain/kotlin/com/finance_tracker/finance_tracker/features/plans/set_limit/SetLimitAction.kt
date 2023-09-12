package com.finance_tracker.finance_tracker.features.plans.set_limit

interface SetLimitAction {
    data class DismissDialog(
        val dialogKey: String
    ): SetLimitAction
}
