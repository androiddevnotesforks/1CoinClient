package com.finance_tracker.finance_tracker.core.common.snackbar

import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarState

data class SnackbarStateWithScreenParams(
    val snackbarState: SnackbarState,
    val screenKey: String,
    val showOnCurrentScreen: Boolean
)