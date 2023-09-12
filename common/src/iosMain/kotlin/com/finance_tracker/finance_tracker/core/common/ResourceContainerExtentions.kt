package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.ResourceContainer

@Suppress("NotImplementedDeclaration")
actual fun ResourceContainer<FileResource>.getCategoryIconFile(
    context: Context,
    fileResourceName: String
): FileResource? {
    return runCatching {
        FileResource(
            fileName = fileResourceName,
            extension = "svg"
        )
    }.getOrDefault(null)
}