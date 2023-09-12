package com.finance_tracker.finance_tracker.data.repositories.export_import

import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.finance_tracker.finance_tracker.core.common.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

actual class FileWriter actual constructor(
    private val context: Context
) {
    
    actual suspend fun zipFiles(
        directoryUri: String,
        name: String,
        files: List<FileData>
    ): String {
        return withContext(Dispatchers.IO) {
            val output = ByteArrayOutputStream()
            try {
                ZipOutputStream(output).use { zos ->
                    val entries = files.map { "${it.name}.${it.ext}" }

                    entries.forEachIndexed { index, fileName ->
                        zos.putNextEntry(ZipEntry(fileName))
                        zos.write(files[index].content.toByteArray())
                        zos.closeEntry()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val directory = DocumentFile.fromTreeUri(context, directoryUri.toUri())!!
            val file = directory.createFile("text/*", "$name.zip")!!
            val fileDescriptor = context.contentResolver.openFileDescriptor(file.uri, "w")!!
            val outputStream = FileOutputStream(fileDescriptor.fileDescriptor)
            outputStream.write(output.toByteArray())
            fileDescriptor.close()
            outputStream.close()

            return@withContext file.uri.toString()
        }
    }
}