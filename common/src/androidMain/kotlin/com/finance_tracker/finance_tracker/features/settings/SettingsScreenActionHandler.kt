package com.finance_tracker.finance_tracker.features.settings

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString

@Suppress("CyclomaticComplexMethod")
fun handleAction(
    action: SettingsAction,
    uriHandler: UriHandler,
    clipboardManager: ClipboardManager,
    pickFileLauncher: ManagedActivityResultLauncher<String, Uri?>,
    pickDirectoryLauncher: ManagedActivityResultLauncher<Uri?, Uri?>,
) {
    when (action) {
        is SettingsAction.OpenUri -> {
            uriHandler.openUri(action.uri)
        }

        is SettingsAction.CopyUserId -> {
            clipboardManager.setText(AnnotatedString(action.userId))
        }

        SettingsAction.ChooseExportDirectory -> {
            pickDirectoryLauncher.launch(null)
        }

        SettingsAction.ChooseImportFile -> {
            pickFileLauncher.launch("application/zip")
        }
    }

}