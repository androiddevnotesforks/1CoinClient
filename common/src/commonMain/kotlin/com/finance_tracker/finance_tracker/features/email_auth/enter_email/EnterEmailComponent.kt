package com.finance_tracker.finance_tracker.features.email_auth.enter_email

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel

class EnterEmailComponent(
    componentContext: ComponentContext,
    private val openEnterOtpScreen: (String) -> Unit,
    private val close: () -> Unit
) : BaseComponent<EnterEmailViewModel>(componentContext) {

    override val viewModel: EnterEmailViewModel = injectViewModel()

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                is Action.OpenEnterOtpScreen -> openEnterOtpScreen(action.email)
                Action.Close -> close()
            }
        }
    }

    sealed interface Action {
        data class OpenEnterOtpScreen(val email: String) : Action
        data object Close : Action
    }
}