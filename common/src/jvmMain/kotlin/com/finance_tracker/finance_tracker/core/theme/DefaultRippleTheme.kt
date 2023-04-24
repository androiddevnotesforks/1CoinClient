package com.finance_tracker.finance_tracker.core.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
object DefaultRippleTheme: RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = CoinTheme.color.content,
        lightTheme = MaterialTheme.colors.isLight
    )

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = CoinTheme.color.content,
        lightTheme = MaterialTheme.colors.isLight
    )
}