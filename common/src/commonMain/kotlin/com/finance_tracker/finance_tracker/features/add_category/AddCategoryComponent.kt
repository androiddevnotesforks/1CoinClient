package com.finance_tracker.finance_tracker.features.add_category

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import org.koin.core.parameter.parametersOf

class AddCategoryComponent(
    componentContext: ComponentContext,
    private val transactionType: TransactionType,
    private val category: Category?,
    private val close: () -> Unit
) : BaseComponent<AddCategoryViewModel>(componentContext) {

    override val viewModel: AddCategoryViewModel = injectViewModel {
        parametersOf(
            AddCategoryScreenParams(
                transactionType = transactionType,
                category = category
            )
        )
    }

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                Action.CloseScreen -> close()
            }
        }
    }

    interface Action {
        object CloseScreen : Action
    }
}