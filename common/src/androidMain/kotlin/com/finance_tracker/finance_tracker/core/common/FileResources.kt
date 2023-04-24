package com.finance_tracker.finance_tracker.core.common

import android.content.Context
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.ResourceContainer

actual fun ResourceContainer<FileResource>.getCategoryIconFile(
    context: Context,
    fileResourceName: String
): FileResource? {
    return runCatching {
        FileResource(context.resources.getIdentifier(fileResourceName, "raw", context.packageName))
    }.getOrDefault(null)
}