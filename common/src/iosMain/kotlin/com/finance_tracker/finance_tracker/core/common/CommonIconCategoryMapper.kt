package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.desc.image.ImageDescResource
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

actual fun String.toCategoryFileResource(): ImageDescResource {
    return categoriesToFileResources[this]?.let(::ImageDescResource) ?: error(
        "No category file resource for string '$this' in map $categoriesToFileResources"
    )
}

actual fun ImageDescResource.toCategoryString(): String {
    return fileResourcesToCategories.mapKeys { it.key?.assetImageName }[resource.assetImageName] ?: error(
        "No string for category FileResource's fileName: '${resource.assetImageName}' in map $fileResourcesToCategories"
    )
}