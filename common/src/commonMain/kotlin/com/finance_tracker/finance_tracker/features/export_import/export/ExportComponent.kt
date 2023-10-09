package com.finance_tracker.finance_tracker.features.export_import.export

import com.arkivanov.decompose.ComponentContext
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel

class ExportComponent(
    componentContext: ComponentContext,
    val uri: String,
    private val close: () -> Unit
) : BaseComponent<ExportViewModel>(componentContext) {

    override val viewModel: ExportViewModel = injectViewModel()

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