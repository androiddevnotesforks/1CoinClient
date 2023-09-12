package com.finance_tracker.finance_tracker.core.common.date.models

import kotlinx.datetime.Month

data class YearMonth(
    val year: Int,
    val month: Month
) {
    operator fun compareTo(other: YearMonth): Int {
        val comparedYears = year.compareTo(other.year)
        if (comparedYears != 0) {
            return comparedYears
        }

        return month.compareTo(other.month)
    }
}