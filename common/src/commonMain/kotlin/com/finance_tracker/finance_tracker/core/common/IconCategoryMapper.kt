package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.resources.FileResource

expect fun String.toCategoryFileResource(): FileResource

expect fun FileResource.toCategoryString(): String