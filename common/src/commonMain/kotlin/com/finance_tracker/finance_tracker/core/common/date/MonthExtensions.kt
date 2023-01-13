package com.finance_tracker.finance_tracker.core.common.date

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.common.stringResource
import kotlinx.datetime.Month
import kotlinx.datetime.number

fun Month.next(): Month {
    return Month(number = number + 1)
}

@Composable
internal fun Month.localizedName(): String {
    return stringResource("month_$number")
}