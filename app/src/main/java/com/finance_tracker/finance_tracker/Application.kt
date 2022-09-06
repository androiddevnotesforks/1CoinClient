package com.finance_tracker.finance_tracker

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        driver = AndroidSqliteDriver(AppDatabase.Schema, this, "AppDatabase.db")
    }

    companion object {
        lateinit var driver: SqlDriver
    }
}