package com.finance_tracker.finance_tracker.data.repositories.export_import

import androidx.core.net.toUri
import com.finance_tracker.finance_tracker.core.common.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

actual class FileReader actual constructor(
    val context: Context
) {
    actual suspend fun readLines(uri: String): List<String> {
        runCatching {
            val lines = mutableListOf<String>()
            val inputStream: InputStream = context.contentResolver
                .openInputStream(uri.toUri())
                ?: return lines

            val reader = BufferedReader(InputStreamReader(inputStream))
            return reader.readLines()
        }.getOrNull() ?: return emptyList()
    }
}