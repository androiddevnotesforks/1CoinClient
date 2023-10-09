package com.finance_tracker.finance_tracker.core.common

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc

expect fun StringResource.localizedString(): String

expect fun StringDesc.localizedString(): String