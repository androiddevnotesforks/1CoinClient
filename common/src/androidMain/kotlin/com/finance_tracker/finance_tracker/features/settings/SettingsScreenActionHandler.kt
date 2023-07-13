package com.finance_tracker.finance_tracker.features.settings

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import com.finance_tracker.finance_tracker.features.export_import.export.ExportLoadingDialog
import com.finance_tracker.finance_tracker.features.export_import.import.ImportLoadingDialog
import com.finance_tracker.finance_tracker.features.settings.views.SendingUsageDataDialog
import com.finance_tracker.finance_tracker.features.settings.views.dialogs.ExportImportBottomDialog
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push

@Suppress("CyclomaticComplexMethod")
fun handleAction(
    viewModel: SettingsScreenViewModel,
    action: SettingsScreenAction,
    baseLocalsStorage: BaseLocalsStorage,
    uriHandler: UriHandler,
    clipboardManager: ClipboardManager,
    pickFileLauncher: ManagedActivityResultLauncher<String, Uri?>,
    pickDirectoryLauncher: ManagedActivityResultLauncher<Uri?, Uri?>,
) {
    val rootController = baseLocalsStorage.rootController

    when (action) {
        SettingsScreenAction.Close -> {
            rootController.popBackStack()
        }

        SettingsScreenAction.OpenCategorySettingsScreen -> {
            rootController.findRootController().push(MainNavigationTree.CategorySettings.name)
        }

        SettingsScreenAction.ShowUsageDataInfoDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.alert) { key ->
                SendingUsageDataDialog(
                    onOkClick = {
                        modalNavController.popBackStack(key, animate = false)
                    }
                )
            }
        }

        SettingsScreenAction.OpenPrivacyScreen -> {
            // TODO
        }

        is SettingsScreenAction.OpenUri -> {
            uriHandler.openUri(action.uri)
        }

        is SettingsScreenAction.CopyUserId -> {
            clipboardManager.setText(AnnotatedString(action.userId))
        }

        SettingsScreenAction.OpenDashboardSettingsScreen -> {
            rootController.findRootController().push(MainNavigationTree.DashboardSettings.name)
        }

        SettingsScreenAction.OpenSelectCurrencyScreen -> {
            rootController.findRootController().push(MainNavigationTree.SelectCurrency.name)
        }

        SettingsScreenAction.OpenExportImportDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.bottomSheet) { key ->
                DisposableEffect(Unit) {
                    viewModel.lastExportImportDialogKey = key
                    onDispose {
                        viewModel.lastExportImportDialogKey = null
                    }
                }
                ExportImportBottomDialog(
                    onExport = viewModel::onExportClick,
                    onImport = viewModel::onImportClick
                )
            }
        }

        is SettingsScreenAction.OpenExportDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.bottomSheet) { key ->
                ExportLoadingDialog(
                    dialogKey = key,
                    uri = action.uri
                )
            }
        }

        SettingsScreenAction.ChooseExportDirectory -> {
            pickDirectoryLauncher.launch(null)
        }

        is SettingsScreenAction.OpenImportDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.bottomSheet) { key ->
                ImportLoadingDialog(
                    dialogKey = key,
                    uri = action.uri
                )
            }
        }

        SettingsScreenAction.ChooseImportFile -> {
            pickFileLauncher.launch("application/zip")
        }

        is SettingsScreenAction.DismissAllDialogs -> {
            val modalNavController = rootController.findModalController()
            modalNavController.clearBackStack()
        }
    }

}