package com.finance_tracker.finance_tracker.features.add_account

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
import com.finance_tracker.finance_tracker.domain.models.Account
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf

class AddAccountComponent(
    componentContext: ComponentContext,
    private val account: Account,
    private val close: () -> Unit
): BaseComponent<AddAccountViewModel>(componentContext) {

    override val viewModel: AddAccountViewModel = injectViewModel { parametersOf(account) }

    private val bottomDialogNavigation = SlotNavigation<BottomDialogConfig>()
    val bottomDialogSlot: Value<ChildSlot<*, BottomDialogChild>> =
        childSlot(
            source = bottomDialogNavigation,
            serializer = BottomDialogConfig.serializer(),
            handleBackButton = true
        ) { config, childComponentContext ->
            when (config) {
                is BottomDialogConfig.DeleteDialog -> {
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
                Action.Close -> {
                    close()
                }
                is Action.ShowDeleteDialog -> {
                    bottomDialogNavigation.activate(
                        BottomDialogConfig.DeleteDialog(
                            title = MR.strings.deleting_account.desc()
                        )
                    )
                }
                Action.DismissBottomDialog -> {
                    bottomDialogNavigation.dismiss()
                }
            }
        }
    }

    @Serializable
    sealed interface BottomDialogConfig {
        @Serializable
        data class DeleteDialog(
            val title: StringDesc
        ) : BottomDialogConfig
    }

    sealed interface BottomDialogChild : DialogCloseable {
        data class DeleteDialog(
            val component: DeleteDialogComponent
        ) : BottomDialogChild
    }

    sealed interface Action {
        data object Close : Action
        data class ShowDeleteDialog(val account: Account): Action
        data object DismissBottomDialog: Action
    }
}