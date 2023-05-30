package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.FileResource

fun String?.toCategoryFileResourceOrDefault(
    defaultFileResource: FileResource = MR.files.ic_category_uncategorized
): FileResource {
    return this?.toCategoryFileResource() ?: defaultFileResource
}