package com.finance_tracker.finance_tracker.core.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
data class CoinShapes(
    val medium: CornerBasedShape = RoundedCornerShape(12.dp)
)