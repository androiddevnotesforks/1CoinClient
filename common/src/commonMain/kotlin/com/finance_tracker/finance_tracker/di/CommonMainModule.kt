package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.AppDatabase
import com.financetracker.financetracker.AccountColorsEntityQueries
import com.financetracker.financetracker.AccountsEntityQueries
import com.financetracker.financetracker.CategoriesEntityQueries
import com.financetracker.financetracker.SmsMessageEntityQueries
import com.financetracker.financetracker.TransactionsEntityQueries
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

fun commonModules() = listOf(CommonMainModule().module)

@Module
@ComponentScan(value = "com.finance_tracker.finance_tracker")
class CommonMainModule {

    @Factory
    fun provideTransactionsEntityQueries(appDatabase: AppDatabase): TransactionsEntityQueries {
        return appDatabase.transactionsEntityQueries
    }

    @Factory
    fun provideSmsMessageEntityQueries(appDatabase: AppDatabase): SmsMessageEntityQueries {
        return appDatabase.smsMessageEntityQueries
    }

    @Factory
    fun provideAccountsEntityQueries(appDatabase: AppDatabase): AccountsEntityQueries {
        return appDatabase.accountsEntityQueries
    }

    @Factory
    fun provideCategoriesEntityQueries(appDatabase: AppDatabase): CategoriesEntityQueries {
        return appDatabase.categoriesEntityQueries
    }

    @Factory
    fun provideAccountColorsEntityQueries(appDatabase: AppDatabase): AccountColorsEntityQueries {
        return appDatabase.accountColorsEntityQueries
    }
}