package com.finance_tracker.finance_tracker.features.category_settings

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
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.serialization.Serializable

class CategorySettingsComponent(
    componentContext: ComponentContext,
    private val close: () -> Unit,
    private val openAddCategoryScreen: (TransactionTypeTab) -> Unit,
    private val openEditCategoryScreen: (TransactionType, Category) -> Unit
) : BaseComponent<CategorySettingsViewModel>(componentContext) {

    override val viewModel: CategorySettingsViewModel = injectViewModel()

    private val bottomDialogNavigation: SlotNavigation<BottomDialogConfig> = SlotNavigation()
    val bottomDialogSlot: Value<ChildSlot<*, BottomDialogChild>> = childSlot(
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
                is Action.OpenAddCategoryScreen -> {
                    openAddCategoryScreen(action.selectedTransactionTypeTab)
                }
                is Action.OpenEditCategoryScreen -> {
                    openEditCategoryScreen(
                        action.transactionType,
                        action.category
                    )
                }
                is Action.OpenDeleteDialog -> {
                    bottomDialogNavigation.activate(
                        BottomDialogConfig.DeleteDialog(
                            title = MR.strings.deleting_category.desc()
                        )
                    )
                }
                is Action.DismissBottomDialog -> {
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

    interface Action {
        data class OpenAddCategoryScreen(
            val selectedTransactionTypeTab: TransactionTypeTab
        ): Action

        data class OpenEditCategoryScreen(
            val transactionType: TransactionType,
            val category: Category
        ) : Action
        data object Close: Action
        data class OpenDeleteDialog(val category: Category): Action
        data object DismissBottomDialog: Action
    }
}