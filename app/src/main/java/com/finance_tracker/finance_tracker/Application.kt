package com.finance_tracker.finance_tracker

import android.app.Application
import com.financetracker.financetracker.TransactionsEntity
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        val driver = AndroidSqliteDriver(AppDatabase.Schema, this, "AppDatabase.db")
        database = AppDatabase(
            driver = driver,
            TransactionsEntityAdapter = TransactionsEntity.Adapter(
                typeAdapter = EnumColumnAdapter()
            )
        )
    }

    companion object {
        lateinit var database: AppDatabase
    }
}