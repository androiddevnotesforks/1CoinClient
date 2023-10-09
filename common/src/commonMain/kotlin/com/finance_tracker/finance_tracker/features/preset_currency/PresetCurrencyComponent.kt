package com.finance_tracker.finance_tracker.features.preset_currency

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel

class PresetCurrencyComponent(
    componentContext: ComponentContext,
    private val openMainScreen: () -> Unit
) : BaseComponent<PresetCurrencyViewModel>(componentContext) {

    override val viewModel: PresetCurrencyViewModel = injectViewModel()

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                Action.OpenMainScreen -> openMainScreen()
            }
        }
    }

    sealed interface Action {
        data object OpenMainScreen: Action
    }
}