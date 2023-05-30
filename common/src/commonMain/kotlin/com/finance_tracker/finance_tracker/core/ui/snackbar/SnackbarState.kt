package com.finance_tracker.finance_tracker.core.ui.snackbar

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.StringResource

sealed interface SnackbarState {

    val iconResId: FileResource
    val textResId: StringResource
    val actionState: SnackbarActionState

    data class Success(
        override val textResId: StringResource,
        override val actionState: SnackbarActionState
    ): SnackbarState {
        override val iconResId: FileResource = MR.files.ic_check_circle
    }

    data class Error(
        override val textResId: StringResource,
        override val actionState: SnackbarActionState
    ): SnackbarState {
        override val iconResId: FileResource = MR.files.ic_cross_circle
    }

    data class Information(
        override val iconResId: FileResource,
        override val textResId: StringResource,
        override val actionState: SnackbarActionState
    ): SnackbarState
}