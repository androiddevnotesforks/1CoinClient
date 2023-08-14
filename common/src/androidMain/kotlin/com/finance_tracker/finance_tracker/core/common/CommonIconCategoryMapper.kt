package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.getImageByFileName

private val categories by lazy {
    List(63) {
        val index = it + 1
        val categoryId = "ic_category_$index"
        categoryId to MR.images.getImageByFileName(categoryId)
    }
}
private val categoriesToFileResources = categories.associate { it }
private val fileResourcesToCategories = categories.associate { (categoryId, fileResource) ->
    fileResource to categoryId
}

actual fun String.toCategoryFileResource(): ImageResource {
    return categoriesToFileResources[this] ?: error(
        "No category file resource for string '$this' in map $categoriesToFileResources"
    )
}

actual fun ImageResource.toCategoryString(): String {
    return fileResourcesToCategories.mapKeys { it.key?.drawableResId }[drawableResId] ?: error(
        "No string for category FileResource's rawResId: '$drawableResId' in map $fileResourcesToCategories"
    )
}