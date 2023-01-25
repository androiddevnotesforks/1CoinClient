package com.finance_tracker.finance_tracker.presentation.dashboard_settings

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage

sealed interface DashboardSettingsAction {
    object Close: DashboardSettingsAction
}

fun handleAction(
    action: DashboardSettingsAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    when (action) {
        DashboardSettingsAction.Close -> {
            baseLocalsStorage.rootController.popBackStack()
        }
    }
}