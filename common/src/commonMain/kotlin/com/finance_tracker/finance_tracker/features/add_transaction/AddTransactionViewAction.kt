package com.finance_tracker.finance_tracker.features.add_transaction

import com.finance_tracker.finance_tracker.domain.models.Transaction

sealed interface AddTransactionViewAction {

    data class DisplayDeleteTransactionDialog(
       val transaction: Transaction?
    ): AddTransactionViewAction

    data object DisplayWrongCalculationDialog: AddTransactionViewAction

    data object DisplayNegativeAmountDialog: AddTransactionViewAction

    data object DismissBottomDialog: AddTransactionViewAction

    data object DismissAlertDialog: AddTransactionViewAction
}