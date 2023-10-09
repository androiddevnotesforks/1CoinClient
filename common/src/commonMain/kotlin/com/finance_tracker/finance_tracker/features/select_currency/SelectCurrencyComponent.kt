package com.finance_tracker.finance_tracker.features.select_currency

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel

class SelectCurrencyComponent(
    componentContext: ComponentContext,
    val back: () -> Unit
) : BaseComponent<SelectCurrencyViewModel>(componentContext) {

    override val viewModel: SelectCurrencyViewModel = injectViewModel()

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