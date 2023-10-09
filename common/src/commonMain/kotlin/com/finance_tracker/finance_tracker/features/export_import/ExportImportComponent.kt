package com.finance_tracker.finance_tracker.features.export_import

import com.arkivanov.decompose.ComponentContext

class ExportImportComponent(
    componentContext: ComponentContext,
    val onExport: () -> Unit,
    val onImport: () -> Unit
) : ComponentContext by componentContext