package com.finance_tracker.finance_tracker.features.settings.views.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.ui.dialogs.BottomSheetDialogSurface
import com.finance_tracker.finance_tracker.features.settings.views.items.ExportItem
import com.finance_tracker.finance_tracker.features.settings.views.items.ImportItem

@Composable
@Suppress("ReusedModifierInstance")
internal fun ExportImportBottomDialog(
    onExport: () -> Unit,
    onImport: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomSheetDialogSurface {
        Column(modifier = modifier) {
            ExportItem(onClick = onExport)
            ImportItem(onClick = onImport)
        }
    }
}