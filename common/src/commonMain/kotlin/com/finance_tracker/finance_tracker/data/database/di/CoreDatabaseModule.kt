package com.finance_tracker.finance_tracker.data.database.di

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.zeroPrefixed
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.data.database.DriverFactory
import com.financetracker.financetracker.data.AccountsEntity
import com.financetracker.financetracker.data.TransactionsEntity
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.EnumColumnAdapter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val coreDatabaseModule = module {
    singleOf(::DatabaseInitializer)
    singleOf(::provideAppDatabase)
    factory { get<AppDatabase>().transactionsEntityQueries }
    factory { get<AppDatabase>().smsMessageEntityQueries }
    factory { get<AppDatabase>().accountsEntityQueries }
    factory { get<AppDatabase>().categoriesEntityQueries }
    factory { get<AppDatabase>().currencyRatesEntityQueries }
}

private fun provideAppDatabase(driverFactory: DriverFactory): AppDatabase {

    val dateAdapter = object : ColumnAdapter<LocalDateTime, String> {

        // Format: "yyyy-MM-ddTHH:mm:ssZ"

        override fun decode(databaseValue: String): LocalDateTime {
            return Instant.parse(databaseValue).toLocalDateTime(TimeZone.currentSystemDefault())
        }

        override fun encode(value: LocalDateTime): String {
            return "${value.year}-${value.monthNumber.zeroPrefixed(2)}-" +
                    "${value.dayOfMonth.zeroPrefixed(2)}T" +
                    "${value.hour.zeroPrefixed(2)}:" +
                    "${value.minute.zeroPrefixed(2)}:" +
                    "${value.second.zeroPrefixed(2)}Z"
        }
    }

    val driver = driverFactory.createDriver()
    return AppDatabase(
        driver = driver,
        TransactionsEntityAdapter = TransactionsEntity.Adapter(
            typeAdapter = EnumColumnAdapter(),
            insertionDateAdapter = dateAdapter,
            dateAdapter = dateAdapter
        ),
        AccountsEntityAdapter = AccountsEntity.Adapter(
            typeAdapter = EnumColumnAdapter(),
        )
    )
}