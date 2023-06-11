package com.finance_tracker.finance_tracker.core.common.date

import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Suppress("UnnecessaryParentheses")
fun YearMonth.minusMonth(value: Int): YearMonth {
    val localDate = LocalDate(
        year = year,
        month = month,
        dayOfMonth = 1
    ).minus(DatePeriod(months = value))

    return YearMonth(
        year = localDate.year,
        month = localDate.month
    )
}

@Suppress("UnnecessaryParentheses")
fun YearMonth.plusMonth(value: Int): YearMonth {
    val localDate = LocalDate(
        year = year,
        month = month,
        dayOfMonth = 1
    ).plus(DatePeriod(months = value))

    return YearMonth(
        year = localDate.year,
        month = localDate.month
    )
}