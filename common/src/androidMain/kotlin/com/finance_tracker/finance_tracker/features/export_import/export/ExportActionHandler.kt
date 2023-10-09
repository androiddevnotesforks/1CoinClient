package com.finance_tracker.finance_tracker.features.export_import.export

import android.content.Context
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.finance_tracker.finance_tracker.core.common.shareFile
import java.io.FileOutputStream

fun handleAction(
    action: ExportAction,
    context: Context
) {
    when (action) {
        is ExportAction.OpenShareSheet -> {
            context.shareFile(
                uri = action.uri,
                type = "application/zip"
            )
        }

        is ExportAction.SaveFile -> {
            runCatching {
                val directory = DocumentFile.fromTreeUri(context, action.directoryUri.toUri())!!
                val file = directory.createFile("text/*", "fileName.txt")!!
                val pfd = context.contentResolver.openFileDescriptor(file.uri, "w")!!
                val fos = FileOutputStream(pfd.fileDescriptor)
                fos.write("content".toByteArray())
                pfd.close()
                fos.close()
            }
        }
    }
}