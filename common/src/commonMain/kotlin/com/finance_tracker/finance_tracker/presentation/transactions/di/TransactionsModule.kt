package com.finance_tracker.finance_tracker.presentation.transactions.di

import com.finance_tracker.finance_tracker.presentation.transactions.TransactionsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val transactionsModule = module {
    factoryOf(::TransactionsViewModel)
}