package com.finance_tracker.finance_tracker.presentation.settings_sheet

import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.presentation.settings_sheet.views.SendingUsageDataDialog
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push

sealed interface SettingsSheetAction {
    data class OpenUri(val uri: String): SettingsSheetAction
    object OpenCategorySettingsScreen: SettingsSheetAction
    data class DismissDialog(val dialogKey: String): SettingsSheetAction
    object ShowUsageDataInfoDialog: SettingsSheetAction
    data class CopyUserId(val userId: String): SettingsSheetAction
}

fun handleAction(
    action: SettingsSheetAction,
    baseLocalsStorage: BaseLocalsStorage,
    clipboardManager: ClipboardManager,
    uriHandler: UriHandler
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        is SettingsSheetAction.OpenUri -> {
            uriHandler.openUri(action.uri)
        }
        is SettingsSheetAction.DismissDialog -> {
            rootController.findModalController().popBackStack(
                key = action.dialogKey,
                animate = true
            )
        }
        SettingsSheetAction.OpenCategorySettingsScreen -> {
            rootController.findRootController().push(MainNavigationTree.CategorySettings.name)
        }
        SettingsSheetAction.ShowUsageDataInfoDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.alert) { key ->
                SendingUsageDataDialog(
                    onOkClick = {
                        modalNavController.popBackStack(key, animate = false)
                    }
                )
            }
        }
        is SettingsSheetAction.CopyUserId -> {
            clipboardManager.setText(AnnotatedString(action.userId))
        }
    }
}