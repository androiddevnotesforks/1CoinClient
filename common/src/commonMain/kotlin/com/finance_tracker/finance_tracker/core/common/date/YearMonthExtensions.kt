package com.finance_tracker.finance_tracker.core.common.date

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

private val currentYear = Clock.System.currentYearMonth().year

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

fun YearMonth.localizedName(context: Context): String {
    return if (currentYear == year) {
        month.localizedName(context)
    } else {
        "${month.localizedName(context)} $year"
    }
}