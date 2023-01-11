package com.finance_tracker.finance_tracker.presentation.detail_account

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import ru.alexgladkov.odyssey.compose.extensions.push

sealed interface DetailAccountAction {
    object CloseScreen: DetailAccountAction
    data class OpenEditAccountScreen(
        val account: Account
    ): DetailAccountAction
    data class OpenEditTransactionScreen(
        val transaction: Transaction
    ): DetailAccountAction
    data class OpenEditAccountScreenFromIconClick(
        val account: Account
    ): DetailAccountAction
}

fun handleAction(
    action: DetailAccountAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        DetailAccountAction.CloseScreen -> {
            val navController = rootController.findRootController()
            navController.popBackStack()
        }
        is DetailAccountAction.OpenEditAccountScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.AddAccount.name,
                params = action.account
            )
        }
        is DetailAccountAction.OpenEditTransactionScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.AddTransaction.name,
                params = action.transaction
            )
        }
        is DetailAccountAction.OpenEditAccountScreenFromIconClick -> {
            val navController = rootController.findRootController()
            navController.push(
                screen = MainNavigationTree.AddAccount.name,
                params = action.account
            )
        }
    }
}