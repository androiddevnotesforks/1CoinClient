package com.finance_tracker.finance_tracker.core.common.date

import kotlinx.datetime.Clock
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Clock.System.currentMonth(): Month {
    return now().toLocalDateTime(TimeZone.UTC).month
}