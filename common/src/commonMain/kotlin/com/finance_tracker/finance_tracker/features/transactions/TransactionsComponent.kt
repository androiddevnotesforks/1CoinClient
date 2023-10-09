package com.finance_tracker.finance_tracker.features.transactions

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.DialogCloseable
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.core.ui.dialogs.DeleteDialogComponent
import com.finance_tracker.finance_tracker.domain.models.Transaction
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.serialization.Serializable

class TransactionsComponent(
    componentContext: ComponentContext,
    private val openTransactionDetailScreen: (Transaction) -> Unit,
    private val openAddTransactionScreen: () -> Unit,
): BaseComponent<TransactionsViewModel>(componentContext) {

    override val viewModel: TransactionsViewModel = injectViewModel()

    private val bottomDialogNavigation = SlotNavigation<DialogConfig>()
    val bottomDialogSlot: Value<ChildSlot<*, BottomDialogChild>> =
        childSlot(
            source = bottomDialogNavigation,
            serializer = DialogConfig.serializer(),
            handleBackButton = true
        ) { config, childComponentContext ->
            when (config) {
                is DialogConfig.DeleteDialog -> {
                    BottomDialogChild.DeleteDialog(
                        DeleteDialogComponent(
                            componentContext = childComponentContext,
                            titleRes = config.title,
                            onCancelClick = bottomDialogNavigation::dismiss,
                            onDeleteClick = bottomDialogNavigation::dismiss
                        )
                    )
                }
            }
        }

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                is Action.OpenTransactionDetailScreen -> {
                    openTransactionDetailScreen(action.transaction)
                }
                Action.DismissBottomDialog -> {
                    bottomDialogNavigation.dismiss()
                }
                is Action.ShowDeleteDialog -> {
                    bottomDialogNavigation.activate(
                        DialogConfig.DeleteDialog(
                            title = MR.strings.deleting_transactions.desc()
                        )
                    )
                }
                Action.OpenAddTransactionScreen -> {
                    openAddTransactionScreen()
                }
            }
        }
    }

    sealed interface BottomDialogChild : DialogCloseable {
        data class DeleteDialog(
            val component: DeleteDialogComponent
        ): BottomDialogChild
    }

    sealed interface Action {
        data class OpenTransactionDetailScreen(val transaction: Transaction): Action
        data object DismissBottomDialog: Action
        data class ShowDeleteDialog(val selectedItemsCount: Int): Action
        data object OpenAddTransactionScreen: Action
    }

    @Serializable
    sealed interface DialogConfig {
        @Serializable
        data class DeleteDialog(
            val title: StringDesc
        ): DialogConfig
    }
}