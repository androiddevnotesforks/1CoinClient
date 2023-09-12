package com.finance_tracker.finance_tracker.features.export_import.export

import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.finance_tracker.finance_tracker.core.common.shareFile
import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import java.io.FileOutputStream

fun handleAction(
    action: ExportAction,
    baseLocalsStorage: BaseLocalsStorage
) {
    val rootController = baseLocalsStorage.rootController

    when (action) {
        is ExportAction.OpenShareSheet -> {
            baseLocalsStorage.context.shareFile(
                uri = action.uri,
                type = "application/zip"
            )
        }

        is ExportAction.DismissDialog -> {
            val modalNavController = rootController.findModalController()
            modalNavController.popBackStack(action.dialogKey, animate = true)
        }

        is ExportAction.SaveFile -> {
            runCatching {
                val directory = DocumentFile.fromTreeUri(baseLocalsStorage.context, action.directoryUri.toUri())!!
                val file = directory.createFile("text/*", "fileName.txt")!!
                val pfd = baseLocalsStorage.context.contentResolver.openFileDescriptor(file.uri, "w")!!
                val fos = FileOutputStream(pfd.fileDescriptor)
                fos.write("content".toByteArray())
                pfd.close()
                fos.close()
            }
        }
    }
}