package com.finance_tracker.finance_tracker.features.settings.views.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.features.export_import.ExportImportComponent
import com.finance_tracker.finance_tracker.features.settings.views.items.ExportItem
import com.finance_tracker.finance_tracker.features.settings.views.items.ImportItem

@Composable
@Suppress("ReusedModifierInstance")
fun ExportImportBottomDialog(
    component: ExportImportComponent,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ExportItem(onClick = { component.onExport() })
        ImportItem(onClick = { component.onImport() })
    }
}