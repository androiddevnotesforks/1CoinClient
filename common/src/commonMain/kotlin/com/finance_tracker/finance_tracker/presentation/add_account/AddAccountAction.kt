package com.finance_tracker.finance_tracker.presentation.add_account

import androidx.compose.material.ScaffoldState
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.ui.DeleteDialog
import com.finance_tracker.finance_tracker.domain.models.Account
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.extensions.present

sealed interface AddAccountAction {
    object Close: AddAccountAction
    data class ShowToast(
        val textId: String
    ): AddAccountAction
    data class ShowDeleteDialog(val account: Account): AddAccountAction
    data class DismissDeleteDialog(val dialogKey: String): AddAccountAction
    data class BackToScreen(val screenName: String): AddAccountAction
}

fun handleAction(
    action: AddAccountAction,
    baseLocalsStorage: BaseLocalsStorage,
    scaffoldState: ScaffoldState,
    onConfirmDeletingClick: (account: Account, dialogKey: String) -> Unit,
    onCancelDeletingClick: (account: Account, dialogKey: String) -> Unit
) {
    val rootController = baseLocalsStorage.rootController
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
        is AddAccountAction.ShowDeleteDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.alert) { key ->
                DeleteDialog(
                    titleEntity = stringResource("account"),
                    onCancelClick = { onCancelDeletingClick.invoke(action.account, key) },
                    onDeleteClick = { onConfirmDeletingClick.invoke(action.account, key) }
                )
            }
        }
        is AddAccountAction.DismissDeleteDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.popBackStack(action.dialogKey, animate = false)
        }
        is AddAccountAction.BackToScreen -> {
            val navController = rootController.findRootController()
            navController.backToScreen(action.screenName)
        }
    }
}