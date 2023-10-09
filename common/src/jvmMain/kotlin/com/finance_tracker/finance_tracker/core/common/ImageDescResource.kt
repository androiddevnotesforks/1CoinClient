package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.desc.image.ImageDesc
import dev.icerock.moko.resources.desc.image.ImageDescResource

@Composable
fun painterDescResource(imageDesc: ImageDesc): Painter {
    return painterDescResource(imageDesc as ImageDescResource)
}

@Composable
fun painterDescResource(imageDesc: ImageDescResource): Painter {
    return painterResource(imageDesc.resource)
}