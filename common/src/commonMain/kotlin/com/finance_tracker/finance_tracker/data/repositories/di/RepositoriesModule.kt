package com.finance_tracker.finance_tracker.data.repositories.di

import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.data.repositories.AuthRepository
import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.data.repositories.DashboardSettingsRepository
import com.finance_tracker.finance_tracker.data.repositories.PlansRepository
import com.finance_tracker.finance_tracker.data.repositories.ThemeRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.data.repositories.UserRepository
import com.finance_tracker.finance_tracker.data.repositories.export_import.ExportImportRepository
import com.finance_tracker.finance_tracker.data.repositories.export_import.FileReader
import com.finance_tracker.finance_tracker.data.repositories.export_import.FileWriter
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val repositoriesModule = module {
    factoryOf(::AccountsRepository)
    factoryOf(::TransactionsRepository)
    factoryOf(::CategoriesRepository)
    factoryOf(::CurrenciesRepository)
    factoryOf(::UserRepository)
    factoryOf(::DashboardSettingsRepository)
    factoryOf(::AuthRepository)
    factoryOf(::ThemeRepository)
    factoryOf(::PlansRepository)
    factoryOf(::ExportImportRepository)
    factoryOf(::FileReader)
    factoryOf(::FileWriter)
}