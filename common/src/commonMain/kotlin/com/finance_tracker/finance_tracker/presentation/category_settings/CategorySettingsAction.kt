package com.finance_tracker.finance_tracker.presentation.category_settings

import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.ui.DeleteDialog
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.domain.models.Category
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push

sealed interface CategorySettingsAction {
    data class OpenAddCategoryScreen(
        val selectedTransactionTypeTab: TransactionTypeTab
    ): CategorySettingsAction
    object CloseScreen: CategorySettingsAction
    data class OpenDeleteDialog(val category: Category): CategorySettingsAction
    data class DismissDialog(val dialogKey: String): CategorySettingsAction
}

fun handleAction(
    action: CategorySettingsAction,
    baseLocalsStorage: BaseLocalsStorage,
    onCancelClick: (category: Category, dialogKey: String) -> Unit,
    onConfirmDeleteClick: (category: Category, dialogKey: String) -> Unit
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        is CategorySettingsAction.OpenAddCategoryScreen -> {
            val navController = rootController.findRootController()
            navController.push(
                MainNavigationTree.AddCategory.name,
                params = action.selectedTransactionTypeTab
            )
        }
        CategorySettingsAction.CloseScreen -> {
            val navController = rootController.findRootController()
            navController.popBackStack()
        }
        is CategorySettingsAction.OpenDeleteDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.present(DialogConfigurations.alert) { key ->
                DeleteDialog(
                    titleEntity = stringResource("category"),
                    onCancelClick = { onCancelClick.invoke(action.category, key) },
                    onDeleteClick = { onConfirmDeleteClick.invoke(action.category, key) }
                )
            }
        }
        is CategorySettingsAction.DismissDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.popBackStack(action.dialogKey, animate = false)
        }
    }
}