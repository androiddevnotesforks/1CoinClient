package com.finance_tracker.finance_tracker.core.ui.snackbar

sealed interface SnackbarActionState {

    val onAction: () -> Unit

    data class Undo(
        override val onAction: () -> Unit
    ): SnackbarActionState

    data class Close(
        override val onAction: () -> Unit
    ): SnackbarActionState
}