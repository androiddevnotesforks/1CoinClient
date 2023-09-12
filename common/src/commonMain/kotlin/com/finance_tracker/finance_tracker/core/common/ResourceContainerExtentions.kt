package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.ResourceContainer

expect fun ResourceContainer<FileResource>.getCategoryIconFile(
    context: Context,
    fileResourceName: String
): FileResource?