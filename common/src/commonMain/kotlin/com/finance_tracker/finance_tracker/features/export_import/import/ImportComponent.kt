package com.finance_tracker.finance_tracker.features.export_import.import

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel

class ImportComponent(
    componentContext: ComponentContext,
    val uri: String,
    private val close: () -> Unit
) : BaseComponent<ImportViewModel>(componentContext) {
    override val viewModel: ImportViewModel = injectViewModel()

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                is Action.Close -> {
                    close()
                }
            }
        }
    }

    interface Action {
        data object Close: Action
    }
}