package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.desc.ResourceStringDesc
import dev.icerock.moko.resources.desc.StringDesc

@Composable
fun stringDescResource(imageDesc: StringDesc): String {
    return stringDescResource(imageDesc as ResourceStringDesc)
}

@Composable
fun stringDescResource(imageDesc: ResourceStringDesc): String {
    return stringResource(imageDesc.stringRes)
}