package com.finance_tracker.finance_tracker.features.welcome

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel

class WelcomeComponent(
    componentContext: ComponentContext,
    private val openEmailAuthScreen: () -> Unit,
    private val openMainScreen: () -> Unit
) : BaseComponent<WelcomeViewModel>(componentContext) {

    override val viewModel: WelcomeViewModel = injectViewModel()

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                Action.OpenEmailAuthScreen -> openEmailAuthScreen()
                Action.OpenMainScreen -> openMainScreen()
            }
        }
    }

    sealed interface Action {
        data object OpenEmailAuthScreen : Action
        data object OpenMainScreen : Action
    }
}