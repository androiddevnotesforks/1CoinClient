package com.finance_tracker.finance_tracker.core.common.view_models

import com.finance_tracker.finance_tracker.core.common.globalKoin
import com.finance_tracker.finance_tracker.core.common.snackbar.SnackbarManager
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarState

private val snackbarManager = globalKoin.get<SnackbarManager>()

fun ComponentViewModel<*, *>.showCurrentScreenSnackbar(snackbarState: SnackbarState) {
    showSnackbar(
        snackbarState = snackbarState,
        showOnCurrentScreen = true
    )
}

fun ComponentViewModel<*, *>.showPreviousScreenSnackbar(snackbarState: SnackbarState) {
    showSnackbar(
        snackbarState = snackbarState,
        showOnCurrentScreen = false
    )
}

@Suppress("UnusedReceiverParameter")
fun ComponentViewModel<*, *>.hideSnackbar() {
    snackbarManager.hideSnackbar()
}

private fun ComponentViewModel<*, *>.showSnackbar(
    snackbarState: SnackbarState,
    showOnCurrentScreen: Boolean
) {
    snackbarManager.showSnackbar(
        screenKey = screenKey,
        showOnCurrentScreen = showOnCurrentScreen,
        snackbarState = snackbarState
    )
}