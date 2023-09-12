package com.finance_tracker.finance_tracker.data.repositories.export_import

import androidx.core.net.toUri
import com.finance_tracker.finance_tracker.core.common.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.zip.ZipFile

actual class FileReader actual constructor(
    val context: Context
) {
    actual suspend fun readLines(uri: String): List<String> {
        return runCatching {
            getBufferedReader(uri)?.readLines()
        }.getOrNull() ?: emptyList()
    }

    actual suspend fun readText(uri: String): String {
        return runCatching {
            getBufferedReader(uri)?.readText()
        }.getOrNull().orEmpty()
    }

    private fun getBufferedReader(uri: String): BufferedReader? {
        val inputStream: InputStream = context.contentResolver
            .openInputStream(uri.toUri())
            ?: return null

        return BufferedReader(InputStreamReader(inputStream))
    }

    private fun readBytes(uri: String): ByteArray {
        return context.contentResolver
            .openInputStream(uri.toUri())
            .use { inputStream ->
                inputStream?.readBytes() ?: byteArrayOf()
            }
    }

    actual suspend fun unzipFiles(
        zipFilePath: String,
        destinationDirectoryPath: String
    ): Map<String, String> {
        return buildMap {
            val fullDestinationDirectoryPath = context.filesDir.path +
                    File.separator + destinationDirectoryPath
            withContext(Dispatchers.IO) {
                File(fullDestinationDirectoryPath).run {
                    if (!exists()) {
                        mkdirs()
                    }
                }
                val zipFile = File("$fullDestinationDirectoryPath/import.zip").apply {
                    createNewFile()
                    writeBytes(readBytes(zipFilePath))
                }

                ZipFile(zipFile).use { zip ->
                    zip.entries().asSequence().forEach { entry ->
                        zip.getInputStream(entry).use { input ->
                            val filePath = fullDestinationDirectoryPath + File.separator + entry.name
                            if (!entry.isDirectory) {
                                val file = File(filePath)
                                extractFile(input, filePath)
                                put(entry.name, file.toUri().toString())
                            } else {
                                val dir = File(filePath)
                                dir.mkdir()
                            }
                        }
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun extractFile(inputStream: InputStream, destFilePath: String) {
        val bos = BufferedOutputStream(FileOutputStream(destFilePath))
        val bytesIn = ByteArray(BUFFER_SIZE)
        var read: Int
        while (inputStream.read(bytesIn).also { read = it } != -1) {
            bos.write(bytesIn, 0, read)
        }
        bos.close()
    }

    companion object {
        private const val BUFFER_SIZE = 4096
    }

}