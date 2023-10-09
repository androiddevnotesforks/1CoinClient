package com.finance_tracker.finance_tracker.features.dashboard_settings

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel

class DashboardSettingsComponent(
    componentContext: ComponentContext,
    val back: () -> Unit
) : BaseComponent<DashboardSettingsViewModel>(componentContext) {

    override val viewModel: DashboardSettingsViewModel = injectViewModel()

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                Action.Back -> back()
            }
        }
    }

    sealed interface Action {
        data object Back : Action
    }
}