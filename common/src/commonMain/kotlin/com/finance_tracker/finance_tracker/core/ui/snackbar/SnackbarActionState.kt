package com.finance_tracker.finance_tracker.core.ui.snackbar

sealed interface SnackbarActionState {

    val onAction: suspend () -> Unit

    data class Undo(
        override val onAction: suspend () -> Unit
    ): SnackbarActionState

    data class Close(
        override val onAction: suspend () -> Unit
    ): SnackbarActionState
}