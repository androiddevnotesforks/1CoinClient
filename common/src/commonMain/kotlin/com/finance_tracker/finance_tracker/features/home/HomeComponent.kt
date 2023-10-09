package com.finance_tracker.finance_tracker.features.home

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction

class HomeComponent(
    componentContext: ComponentContext,
    private val openAccountDetailScreen: (Account) -> Unit,
    private val openAccountsScreen: () -> Unit,
    private val openAddAccountScreen: () -> Unit,
    private val openEditTransactionScreen: (Transaction) -> Unit,
    private val openTransactionsScreen: () -> Unit,
    private val openSettingsScreen: () -> Unit,
): BaseComponent<HomeViewModel>(componentContext) {

    override val viewModel: HomeViewModel = injectViewModel()

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                is Action.OpenAccountDetailScreen -> openAccountDetailScreen(action.account)
                Action.OpenAccountsScreen -> openAccountsScreen()
                Action.OpenAddAccountScreen -> openAddAccountScreen()
                is Action.OpenEditTransactionScreen -> openEditTransactionScreen(action.transaction)
                Action.OpenTransactionsScreen -> openTransactionsScreen()
                Action.OpenSettingsScreen -> openSettingsScreen()
            }
        }
    }

    sealed interface Action {
        object OpenAccountsScreen: Action
        object OpenTransactionsScreen: Action
        data class OpenAccountDetailScreen(
            val account: Account
        ): Action
        object OpenAddAccountScreen: Action
        data class OpenEditTransactionScreen(
            val transaction: Transaction
        ): Action
        object OpenSettingsScreen: Action
    }
}