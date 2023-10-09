package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.desc.image.ImageDescResource

fun ImageResource.asImageDescResource(): ImageDescResource {
    return ImageDescResource(this)
}