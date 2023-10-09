package com.finance_tracker.finance_tracker.features.add_transaction

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.DialogCloseable
import com.finance_tracker.finance_tracker.core.common.decompose_ext.alertDialogSlot
import com.finance_tracker.finance_tracker.core.common.decompose_ext.bottomDialogSlot
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.core.ui.dialogs.DeleteDialogComponent
import com.finance_tracker.finance_tracker.core.ui.dialogs.NotificationDialogComponent
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.toTransactionType
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf

class AddTransactionComponent(
    componentContext: ComponentContext,
    params: AddTransactionScreenParams,
    private val popBackStack: () -> Unit,
    private val openAddAccountScreen: () -> Unit,
    private val openAddCategoryScreen: (TransactionType) -> Unit,
): BaseComponent<AddTransactionViewModel>(componentContext) {

    override val viewModel: AddTransactionViewModel = injectViewModel { parametersOf(params) }
    val transaction = params.transaction

    private val bottomDialogNavigation: SlotNavigation<BottomDialogConfig> = SlotNavigation()
    val bottomDialogSlot: Value<ChildSlot<*, BottomDialogChild>> = bottomDialogSlot(
        source = bottomDialogNavigation,
        serializer = BottomDialogConfig.serializer()
    ) { config, childComponentContext ->
        when (config) {
            BottomDialogConfig.DeleteTransactionDialog -> {
                BottomDialogChild.DeleteTransactionDialog(
                    DeleteDialogComponent(
                        componentContext = childComponentContext,
                        titleRes = MR.strings.deleting_transaction.desc(),
                        onCancelClick = {
                            bottomDialogNavigation.dismiss()
                        },
                        onDeleteClick = {
                            viewModel.onDeleteTransactionClick()
                            bottomDialogNavigation.dismiss()
                        }
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
            is AlertDialogConfig.NotificationDialog -> {
                AlertDialogChild.NotificationDialog(
                    NotificationDialogComponent(
                        componentContext = childComponentContext,
                        textRes = config.text,
                        onOkClick = {
                            alertDialogNavigation.dismiss()
                        }
                    )
                )
            }
        }
    }

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                Action.Close -> {
                    popBackStack()
                }
                is Action.DismissBottomDialog -> {
                    bottomDialogNavigation.dismiss()
                }
                is Action.DismissAlertDialog -> {
                    alertDialogNavigation.dismiss()
                }
                is Action.DisplayDeleteTransactionDialog -> {
                    bottomDialogNavigation.activate(BottomDialogConfig.DeleteTransactionDialog)
                }
                Action.DisplayNegativeAmountDialog -> {
                    alertDialogNavigation.activate(
                        AlertDialogConfig.NotificationDialog(
                            text = MR.strings.add_transaction_negative_balance.desc(),
                        )
                    )
                }
                Action.DisplayWrongCalculationDialog -> {
                    alertDialogNavigation.activate(
                        AlertDialogConfig.NotificationDialog(
                            text = MR.strings.add_transaction_incorrect_calculation.desc(),
                        )
                    )
                }
                Action.OpenAddAccountScreen -> {
                    openAddAccountScreen()
                }
                is Action.OpenAddCategoryScreen -> {
                    openAddCategoryScreen(action.type.toTransactionType())
                }
            }
        }
    }

    fun onBackClick() {
        popBackStack()
    }

    sealed interface AlertDialogChild {
        data class NotificationDialog(
            val component: NotificationDialogComponent
        ) : AlertDialogChild
    }


    @Serializable
    sealed interface AlertDialogConfig {

        @Serializable
        data class NotificationDialog(
            val text: StringDesc
        ) : AlertDialogConfig
    }

    sealed interface BottomDialogChild: DialogCloseable {

        data class DeleteTransactionDialog(
            val component: DeleteDialogComponent
        ): BottomDialogChild
    }

    @Serializable
    sealed interface BottomDialogConfig {

        @Serializable
        data object DeleteTransactionDialog: BottomDialogConfig
    }


    sealed interface Action {

        data object Close : Action

        data object DisplayDeleteTransactionDialog: Action

        data object DisplayWrongCalculationDialog: Action

        data object DisplayNegativeAmountDialog: Action

        data object DismissBottomDialog: Action

        data object DismissAlertDialog: Action

        data object OpenAddAccountScreen: Action

        data class OpenAddCategoryScreen(
            val type: TransactionTypeTab
        ): Action
    }
}