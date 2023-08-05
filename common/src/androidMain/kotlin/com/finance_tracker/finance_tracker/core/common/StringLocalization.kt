package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

actual fun StringResource.localizedString(context: Context): String {
    return desc().toString(context)
}

actual fun StringDesc.localizedString(context: Context): String {
    return toString(context)
}