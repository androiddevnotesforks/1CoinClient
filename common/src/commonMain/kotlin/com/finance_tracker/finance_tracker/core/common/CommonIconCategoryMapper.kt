package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.resources.desc.image.ImageDescResource

expect fun String.toCategoryFileResource(): ImageDescResource

expect fun ImageDescResource.toCategoryString(): String