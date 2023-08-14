package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.ResourceContainer
import dev.icerock.moko.resources.getImageByFileName

fun ResourceContainer<ImageResource>.getCategoryIconByNameOrDefault(
    name: String?,
    defaultImageResource: ImageResource = MR.images.ic_category_uncategorized
): ImageResource {
    return name?.let(::getImageByFileName) ?: defaultImageResource
}