package com.finance_tracker.finance_tracker.features.accounts

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.domain.models.Account

class AccountsComponent(
    componentContext: ComponentContext,
    private val close: () -> Unit,
    private val openAddAccountScreen: () -> Unit,
    private val openEditAccountScreen: (Account) -> Unit
): BaseComponent<AccountsViewModel>(componentContext) {

    override val viewModel: AccountsViewModel = injectViewModel()

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                Action.CloseScreen -> close()
                Action.OpenAddAccountScreen -> openAddAccountScreen()
                is Action.OpenEditAccountScreen -> openEditAccountScreen(action.account)
            }
        }
    }

    sealed interface Action {
        data class OpenEditAccountScreen(
            val account: Account
        ): Action
        data object OpenAddAccountScreen: Action
        data object CloseScreen: Action
    }
}