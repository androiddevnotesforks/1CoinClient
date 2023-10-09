package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

actual fun StringResource.localizedString(): String {
    return desc().toString(ActivityContextStorage.getContext())
}

actual fun StringDesc.localizedString(): String {
    return toString(ActivityContextStorage.getContext())
}