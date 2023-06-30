package com.finance_tracker.finance_tracker.features.add_transaction

import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.domain.models.Transaction

sealed interface AddTransactionAction {

    object Close: AddTransactionAction

    data class DisplayDeleteTransactionDialog(
       val transaction: Transaction?
    ): AddTransactionAction

    object DisplayWrongCalculationDialog: AddTransactionAction

    object DisplayNegativeAmountDialog: AddTransactionAction

    data class DismissDialog(
        val dialogKey: String
    ): AddTransactionAction

    object OpenAddAccountScreen: AddTransactionAction

    data class OpenAddCategoryScreen(
        val type: TransactionTypeTab
    ): AddTransactionAction
}