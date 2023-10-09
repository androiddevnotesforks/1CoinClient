package com.finance_tracker.finance_tracker.features.add_account

fun handleAction(
    action: AddAccountAction,
    onHideKeyboard: () -> Unit,
) {
    when (action) {
        AddAccountAction.HideKeyboard -> {
            onHideKeyboard()
        }
    }
}