package com.finance_tracker.finance_tracker.features.add_account

sealed interface AddAccountAction {
    data object HideKeyboard: AddAccountAction
}