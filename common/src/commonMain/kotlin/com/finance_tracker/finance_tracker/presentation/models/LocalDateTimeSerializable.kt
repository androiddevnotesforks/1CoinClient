package com.finance_tracker.finance_tracker.presentation.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class LocalDateTimeSerializable(
    val year: Int,
    val monthNumber: Int,
    val dayOfMonth: Int,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val nanosecond: Int,
)

fun LocalDateTime.toSerializable(): LocalDateTimeSerializable {
    return LocalDateTimeSerializable(
        year = year,
        monthNumber = monthNumber,
        dayOfMonth = dayOfMonth,
        hour = hour,
        minute = minute,
        second = second,
        nanosecond = nanosecond,
    )
}

fun LocalDateTimeSerializable.toDomain(): LocalDateTime {
    return LocalDateTime(
        year = year,
        monthNumber = monthNumber,
        dayOfMonth = dayOfMonth,
        hour = hour,
        minute = minute,
        second = second,
        nanosecond = nanosecond,
    )
}