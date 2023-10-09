package com.finance_tracker.finance_tracker.features.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.DialogCloseable
import com.finance_tracker.finance_tracker.core.common.decompose_ext.alertDialogSlot
import com.finance_tracker.finance_tracker.core.common.decompose_ext.bottomDialogSlot
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.features.export_import.ExportImportComponent
import com.finance_tracker.finance_tracker.features.export_import.export.ExportComponent
import com.finance_tracker.finance_tracker.features.export_import.import.ImportComponent
import com.finance_tracker.finance_tracker.features.sending_usage.SendingUsageComponent
import kotlinx.serialization.Serializable

class SettingsComponent(
    componentContext: ComponentContext,
    val openCategorySettingsScreen: () -> Unit,
    val openDashboardSettingsScreen: () -> Unit,
    val openSelectMainCurrencyScreen: () -> Unit,
    val back: () -> Unit
): BaseComponent<SettingsViewModel>(componentContext) {

    override val viewModel: SettingsViewModel = injectViewModel()

    private val bottomDialogNavigation: SlotNavigation<BottomDialogConfig> = SlotNavigation()
    val bottomDialogSlot: Value<ChildSlot<*, BottomDialogChild>> = bottomDialogSlot(
        source = bottomDialogNavigation,
        serializer = BottomDialogConfig.serializer()
    ) { config, childComponentContext ->
        when (config) {
            BottomDialogConfig.ExportImportDialog -> {
                BottomDialogChild.ExportImportChild(
                    ExportImportComponent(
                        componentContext = childComponentContext,
                        onExport = viewModel::onExportClick,
                        onImport = viewModel::onImportClick
                    )
                )
            }
            is BottomDialogConfig.ExportDialog -> {
                BottomDialogChild.ExportChild(
                    ExportComponent(
                        componentContext = childComponentContext,
                        uri = config.uri,
                        close = { bottomDialogNavigation.dismiss() }
                    )
                )
            }
            is BottomDialogConfig.ImportDialog -> {
                BottomDialogChild.ImportChild(
                    ImportComponent(
                        componentContext = childComponentContext,
                        uri = config.uri,
                        close = { bottomDialogNavigation.dismiss() }
                    )
                )
            }
        }
    }

    private val alertDialogNavigation: SlotNavigation<AlertDialogConfig> = SlotNavigation()
    val alertDialogSlot: Value<ChildSlot<*, AlertDialogChild>> = alertDialogSlot(
        source = alertDialogNavigation,
        serializer = AlertDialogConfig.serializer()
    ) { config, childComponentContext ->
        when (config) {
            AlertDialogConfig.UsageDataInfoDialog -> {
                AlertDialogChild.SendingUsage(
                    SendingUsageComponent(
                        componentContext = childComponentContext,
                        close = { alertDialogNavigation.dismiss() }
                    )
                )
            }
        }
    }

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                Action.OpenCategorySettingsScreen -> openCategorySettingsScreen()
                Action.OpenWidgetsSettingsScreen -> openDashboardSettingsScreen()
                Action.OpenSelectMainCurrencyScreen -> openSelectMainCurrencyScreen()
                Action.Back -> back()
                Action.DismissBottomDialogs -> {
                    bottomDialogNavigation.dismiss()
                }
                is Action.OpenExportDialog -> {
                    bottomDialogNavigation.activate(BottomDialogConfig.ExportDialog(action.uri))
                }
                Action.OpenExportImportDialog -> {
                    bottomDialogNavigation.activate(BottomDialogConfig.ExportImportDialog)
                }
                is Action.OpenImportDialog -> {
                    bottomDialogNavigation.activate(BottomDialogConfig.ImportDialog(action.uri))
                }
                Action.OpenPrivacyScreen -> {
                    // TODO
                }
                Action.ShowUsageDataInfoDialog -> {
                    alertDialogNavigation.activate(AlertDialogConfig.UsageDataInfoDialog)
                }

                Action.DismissAlertDialogs -> {
                    alertDialogNavigation.dismiss()
                }
            }
        }
    }

    sealed interface BottomDialogChild: DialogCloseable {

        data class ExportImportChild(
            val component: ExportImportComponent
        ): BottomDialogChild

        data class ExportChild(
            val component: ExportComponent
        ): BottomDialogChild {
            override val closeable: Boolean = false
        }

        data class ImportChild(
            val component: ImportComponent
        ): BottomDialogChild {
            override val closeable: Boolean = false
        }
    }

    sealed interface AlertDialogChild {
        data class SendingUsage(
            val component: SendingUsageComponent
        ) : AlertDialogChild
    }

    @Serializable
    sealed interface BottomDialogConfig {

        @Serializable
        data object ExportImportDialog : BottomDialogConfig

        @Serializable
        data class ImportDialog(
            val uri: String
        ) : BottomDialogConfig

        @Serializable
        data class ExportDialog(
            val uri: String
        ) : BottomDialogConfig
    }

    @Serializable
    sealed interface AlertDialogConfig {
        @Serializable
        data object UsageDataInfoDialog: AlertDialogConfig
    }

    sealed interface Action {
        data object OpenCategorySettingsScreen : Action
        data object OpenWidgetsSettingsScreen : Action
        data object OpenSelectMainCurrencyScreen : Action
        data object Back : Action
        data object ShowUsageDataInfoDialog: Action
        data object OpenExportImportDialog: Action
        data class OpenExportDialog(val uri: String): Action
        data class OpenImportDialog(val uri: String): Action
        data object DismissBottomDialogs: Action
        data object DismissAlertDialogs: Action
        data object OpenPrivacyScreen: Action
    }
}