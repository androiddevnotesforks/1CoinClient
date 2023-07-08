package com.finance_tracker.finance_tracker.features.settings.views.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.rememberAsyncImagePainter
import com.finance_tracker.finance_tracker.features.settings.views.ListItem
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun ExportImportDataItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        iconLeftPainter = rememberAsyncImagePainter(MR.files.ic_file_transfer),
        text = stringResource(MR.strings.settings_export_import_data),
        onClick = onClick
    )
}