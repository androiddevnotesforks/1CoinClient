package com.finance_tracker.finance_tracker.features.add_account

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.ui.dialogs.DeleteBottomDialog
import com.finance_tracker.finance_tracker.domain.models.Account
import dev.icerock.moko.resources.compose.stringResource
import ru.alexgladkov.odyssey.compose.extensions.present

fun handleAction(
    action: AddAccountAction,
    baseLocalsStorage: BaseLocalsStorage,
    onHideKeyboard: () -> Unit,
    onConfirmDeletingClick: (account: Account, dialogKey: String) -> Unit,
    onCancelDeletingClick: (account: Account, dialogKey: String) -> Unit
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        AddAccountAction.Close -> {
            baseLocalsStorage.rootController.popBackStack()
        }
        AddAccountAction.HideKeyboard -> {
            onHideKeyboard()
        }
        is AddAccountAction.ShowDeleteDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.bottomSheet) { key ->
                DeleteBottomDialog(
                    titleEntity = stringResource(MR.strings.deleting_account),
                    onCancelClick = { onCancelDeletingClick(action.account, key) },
                    onDeleteClick = { onConfirmDeletingClick(action.account, key) }
                )
            }
        }
        is AddAccountAction.DismissDeleteDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.popBackStack(action.dialogKey, animate = true)
        }
    }
}