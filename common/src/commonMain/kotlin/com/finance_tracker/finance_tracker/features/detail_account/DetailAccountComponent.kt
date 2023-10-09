package com.finance_tracker.finance_tracker.features.detail_account

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import org.koin.core.parameter.parametersOf

class DetailAccountComponent(
    componentContext: ComponentContext,
    val account: Account,
    private val close: () -> Unit,
    private val openEditAccountScreen: (Account) -> Unit,
    private val openEditTransactionScreen: (Transaction) -> Unit,
    private val openAddTransactionScreen: (Account) -> Unit,
) : BaseComponent<DetailAccountViewModel>(componentContext) {

    override val viewModel: DetailAccountViewModel = injectViewModel { parametersOf(account) }

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                Action.Close -> close()
                is Action.OpenEditAccountScreen -> openEditAccountScreen(action.account)
                is Action.OpenEditTransactionScreen -> openEditTransactionScreen(action.transaction)
                is Action.OpenAddTransactionScreen -> openAddTransactionScreen(action.account)
            }
        }
    }

    interface Action {
        object Close : Action
        data class OpenEditAccountScreen(
            val account: Account
        ): Action
        data class OpenEditTransactionScreen(
            val transaction: Transaction
        ): Action
        data class OpenAddTransactionScreen(
            val account: Account
        ): Action
    }
}