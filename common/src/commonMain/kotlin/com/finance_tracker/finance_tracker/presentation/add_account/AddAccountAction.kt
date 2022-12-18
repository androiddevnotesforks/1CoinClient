package com.finance_tracker.finance_tracker.presentation.add_account

import androidx.compose.material.ScaffoldState
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import kotlinx.coroutines.launch

sealed class AddAccountAction {
    object Close: AddAccountAction()
    data class ShowToast(
        val textId: String
    ): AddAccountAction()
}

fun handleAction(
    action: AddAccountAction,
    baseLocalsStorage: BaseLocalsStorage,
    scaffoldState: ScaffoldState,
) {
    when (action) {
        AddAccountAction.Close -> {
            baseLocalsStorage.rootController.popBackStack()
        }
        is AddAccountAction.ShowToast -> {
            baseLocalsStorage.coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = getLocalizedString(action.textId, baseLocalsStorage.context),
                    actionLabel = getLocalizedString("got_it", baseLocalsStorage.context)
                )
            }
        }
    }
}