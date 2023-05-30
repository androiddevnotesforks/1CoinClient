package com.finance_tracker.finance_tracker.core.common.view_models

import com.finance_tracker.finance_tracker.core.common.getKoin
import com.finance_tracker.finance_tracker.core.common.snackbar.SnackbarManager
import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarState

private val snackbarManager = getKoin().get<SnackbarManager>()

fun BaseViewModel<*>.showCurrentScreenSnackbar(snackbarState: SnackbarState) {
    showSnackbar(
        snackbarState = snackbarState,
        showOnCurrentScreen = true
    )
}

fun BaseViewModel<*>.showPreviousScreenSnackbar(snackbarState: SnackbarState) {
    showSnackbar(
        snackbarState = snackbarState,
        showOnCurrentScreen = false
    )
}

@Suppress("UnusedReceiverParameter")
fun BaseViewModel<*>.hideSnackbar() {
    snackbarManager.hideSnackbar()
}

private fun BaseViewModel<*>.showSnackbar(
    snackbarState: SnackbarState,
    showOnCurrentScreen: Boolean
) {
    snackbarManager.showSnackbar(
        screenKey = screenKey,
        showOnCurrentScreen = showOnCurrentScreen,
        snackbarState = snackbarState
    )
}