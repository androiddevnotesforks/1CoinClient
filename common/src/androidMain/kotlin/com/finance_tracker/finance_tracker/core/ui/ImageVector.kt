package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource

@Composable
actual fun loadXmlPicture(name: String): ImageVector {
    return with(LocalContext.current) {
        ImageVector.vectorResource(resources.getIdentifier(name, "drawable", packageName))
    }
}