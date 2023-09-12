package com.finance_tracker.finance_tracker.core.common.csv

import com.finance_tracker.finance_tracker.data.repositories.export_import.FileReader

fun List<List<String?>>.toCsv(): String {
    return joinToString("\n") { row ->
        row.duplicateQuotas()
            .quoteItemsWithComma()
            .joinToStringByComma()
    }
}

private fun List<String?>.duplicateQuotas(): List<String?> {
    return map { item ->
        if (item != null && item.contains("\"")) {
            "\"${item.replace("\"", "\"\"")}\""
        } else {
            item
        }
    }
}

private fun List<String?>.quoteItemsWithComma(): List<String?> {
    return map { item ->
        if (item != null && item.contains(",") && !item.contains("\"")) {
            "\"$item\""
        } else {
            item
        }
    }
}

private fun List<String?>.joinToStringByComma(): String {
    return joinToString(",")
}

suspend fun fromCsvFile(fileReader: FileReader, filePath: String): List<List<String?>> {
    return fileReader
        .readLines(filePath)
        .fromCsv()
}

fun List<String>.fromCsv(): List<List<String?>> {
    return dropHeaders()
        .splitStringByComma()
        .map { row ->
            row.map { item ->
                item.handleNulls()
                    ?.removeDuplicatedQuotes()
                    ?.trimQuotes()
            }
        }
}

private fun List<String>.dropHeaders(): List<String> {
    return drop(1)
}

private fun List<String>.splitStringByComma(): List<List<String>> {
    return map { it.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*\$)".toRegex()) }
}

private fun String.handleNulls(): String? {
    return takeIf { it != "null" }
}

private fun String.removeDuplicatedQuotes(): String {
    return replace("\"\"", "\"")
}

private fun String.trimQuotes(): String {
    return if (contains(",") && startsWith("\"") && endsWith("\"")) {
        "^.|.$".toRegex().replace(this, "")
    } else {
        this
    }
}