package com.finance_tracker.finance_tracker.presentation.settings_sheet

import androidx.compose.ui.platform.UriHandler
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push

sealed interface SettingsSheetAction {
    data class OpenUri(val uri: String): SettingsSheetAction
    object OpenCategorySettingsScreen: SettingsSheetAction
    data class DismissDialog(val dialogKey: String): SettingsSheetAction
}

fun handleAction(
    action: SettingsSheetAction,
    baseLocalsStorage: BaseLocalsStorage,
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
    }
}