package com.finance_tracker.finance_tracker.presentation.add_account.di

import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val addAccountModule = module {
    factoryOf(::AddAccountViewModel)
}