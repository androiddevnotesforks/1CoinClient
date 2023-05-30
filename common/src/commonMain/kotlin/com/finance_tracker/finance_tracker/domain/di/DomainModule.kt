package com.finance_tracker.finance_tracker.domain.di

import com.finance_tracker.finance_tracker.domain.interactors.AccountsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.AuthInteractor
import com.finance_tracker.finance_tracker.domain.interactors.CategoriesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.CurrenciesInteractor
import com.finance_tracker.finance_tracker.domain.interactors.DashboardSettingsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.PlansInteractor
import com.finance_tracker.finance_tracker.domain.interactors.ThemeInteractor
import com.finance_tracker.finance_tracker.domain.interactors.UserInteractor
import com.finance_tracker.finance_tracker.domain.interactors.transactions.GetTransactionsForChartUseCase
import com.finance_tracker.finance_tracker.domain.interactors.transactions.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.transactions.UpdateAccountBalanceForTransactionUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val domainModule = module {

    // Transactions
    factoryOf(::TransactionsInteractor)
    factoryOf(::GetTransactionsForChartUseCase)
    factoryOf(::UpdateAccountBalanceForTransactionUseCase)

    factoryOf(::UserInteractor)
    factoryOf(::CurrenciesInteractor)
    factoryOf(::CategoriesInteractor)
    factoryOf(::AccountsInteractor)
    factoryOf(::DashboardSettingsInteractor)
    factoryOf(::AuthInteractor)
    factoryOf(::ThemeInteractor)
    factoryOf(::PlansInteractor)
}