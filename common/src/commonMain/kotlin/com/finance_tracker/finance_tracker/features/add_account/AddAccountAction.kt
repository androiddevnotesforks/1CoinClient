package com.finance_tracker.finance_tracker.features.add_account

import com.finance_tracker.finance_tracker.domain.models.Account

sealed interface AddAccountAction {
    object Close: AddAccountAction
    object HideKeyboard: AddAccountAction
    data class ShowDeleteDialog(val account: Account): AddAccountAction
    data class DismissDeleteDialog(val dialogKey: String): AddAccountAction
}