package com.finance_tracker.finance_tracker.features.plans.set_limit

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.injectViewModel

class SetLimitComponent(
    componentContext: ComponentContext,
    val yearMonth: YearMonth,
    private val dismissDialog: () -> Unit
): BaseComponent<SetLimitViewModel>(componentContext) {

    override val viewModel: SetLimitViewModel = injectViewModel()

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                Action.Dismiss -> {
                    dismissDialog()
                }
            }
        }
    }

    sealed interface Action {
        data object Dismiss : Action
    }
}