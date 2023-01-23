package com.finance_tracker.finance_tracker.data.database

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.OS
import com.finance_tracker.finance_tracker.core.common.OSProvider
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import java.io.File

actual class DriverFactory {

  private val appDataPath by lazy {
    val appName = "1Coin"
    when (OSProvider.os) {
      OS.Windows -> "${System.getProperty("user.home")}\\AppData\\Local\\$appName"
      OS.Mac -> "/Users/${System.getProperty("user.name")}/Library/Application Support/$appName"
      OS.Unix -> ""
      OS.Solaris -> ""
    }
  }

  private val separator by lazy {
    if (OSProvider.os == OS.Windows) {
      "\\"
    } else {
      "/"
    }
  }

  actual fun createDriver(): SqlDriver {
    File(appDataPath).createIfNotExists()
    val filePath = "$appDataPath${separator}AppDatabase.db"
    Class.forName("org.sqlite.JDBC")
    return JdbcSqliteDriver("jdbc:sqlite:$filePath").also {
      if (!File(filePath).exists()) {
        AppDatabase.Schema.create(it)
      }
    }
  }

  private fun File.createIfNotExists() {
    if (!exists()) {
      mkdirs()
    }
  }
}