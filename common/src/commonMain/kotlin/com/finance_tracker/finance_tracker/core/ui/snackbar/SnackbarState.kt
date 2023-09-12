package com.finance_tracker.finance_tracker.core.ui.snackbar

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

sealed interface SnackbarState {

    val iconResId: ImageResource
    val textResId: StringResource
    val actionState: SnackbarActionState

    data class Success(
        override val textResId: StringResource,
        override val actionState: SnackbarActionState
    ): SnackbarState {
        override val iconResId: ImageResource = MR.images.ic_check_circle
    }

    data class Error(
        override val textResId: StringResource,
        override val actionState: SnackbarActionState
    ): SnackbarState {
        override val iconResId: ImageResource = MR.images.ic_cross_circle
    }

    data class Information(
        override val iconResId: ImageResource,
        override val textResId: StringResource,
        override val actionState: SnackbarActionState
    ): SnackbarState
}