package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.resources.ImageResource

expect fun String.toCategoryFileResource(): ImageResource

expect fun ImageResource.toCategoryString(): String