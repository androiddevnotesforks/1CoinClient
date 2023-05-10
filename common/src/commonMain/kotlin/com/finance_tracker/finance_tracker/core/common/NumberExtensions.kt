package com.finance_tracker.finance_tracker.core.common

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode

fun Float.toString(precision: Int): String {
    return this.toDouble().toString(precision)
}

fun Double.toString(precision: Int): String {
    return BigDecimal.fromDouble(
        double = this,
        decimalMode = DecimalMode(
            decimalPrecision = 30,
            roundingMode = RoundingMode.ROUND_HALF_AWAY_FROM_ZERO,
            scale = precision.toLong()
        )
    ).toStringExpanded()
}