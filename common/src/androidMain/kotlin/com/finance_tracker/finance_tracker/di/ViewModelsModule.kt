package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.presentation.accounts.AccountsViewModel
import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountViewModel
import com.finance_tracker.finance_tracker.presentation.add_category.AddCategoryViewModel
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionViewModel
import com.finance_tracker.finance_tracker.presentation.categories.CategorySettingsViewModel
import com.finance_tracker.finance_tracker.presentation.home.HomeViewModel
import com.finance_tracker.finance_tracker.presentation.transactions.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

actual class ViewModelsModule {
    actual fun create(): Module = module {
        viewModelOf(::TransactionsViewModel)
        viewModelOf(::AccountsViewModel)
        viewModelOf(::AddAccountViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::AddTransactionViewModel)
        viewModelOf(::CategorySettingsViewModel)
        viewModelOf(::AddCategoryViewModel)
    }
}