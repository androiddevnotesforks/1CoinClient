package com.finance_tracker.finance_tracker.features.email_auth.enter_otp

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import org.koin.core.parameter.parametersOf

class EnterOtpComponent(
    componentContext: ComponentContext,
    email: String,
    private val close: () -> Unit
) : BaseComponent<EnterOtpViewModel>(componentContext) {

    override val viewModel: EnterOtpViewModel = injectViewModel { parametersOf(email) }

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                is Action.Close -> close()
            }
        }
    }

    sealed interface Action {
        data object Close : Action
    }
}