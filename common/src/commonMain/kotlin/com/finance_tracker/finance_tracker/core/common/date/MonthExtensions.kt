package com.finance_tracker.finance_tracker.core.common.date

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.common.stringResource
import kotlinx.datetime.Month

fun Month.next(): Month {
    return plus(1)
}

@Composable
fun Month.localizedName(): String {
    return stringResource("month_${value}")
}