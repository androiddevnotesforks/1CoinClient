package com.finance_tracker.finance_tracker.di

import android.content.Context
import com.finance_tracker.finance_tracker.AppDatabase
import com.financetracker.financetracker.AccountsEntity
import com.financetracker.financetracker.TransactionsEntity
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import java.text.SimpleDateFormat
import java.util.Date

@Module
@ComponentScan(value = "com.finance_tracker.finance_tracker")
class AppModule {

    @Single
    fun provideAppDatabase(context: Context): AppDatabase {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val dateAdapter = object : ColumnAdapter<Date, String> {
            override fun decode(databaseValue: String) = formatter.parse(databaseValue)!!
            override fun encode(value: Date) = formatter.format(value)
        }
        val driver = AndroidSqliteDriver(AppDatabase.Schema, context, "AppDatabase.db")
        return AppDatabase(
            driver = driver,
            TransactionsEntityAdapter = TransactionsEntity.Adapter(
                typeAdapter = EnumColumnAdapter(),
                dateAdapter = dateAdapter
            ),
            AccountsEntityAdapter = AccountsEntity.Adapter(
                typeAdapter = EnumColumnAdapter(),
            )
        )
    }
}