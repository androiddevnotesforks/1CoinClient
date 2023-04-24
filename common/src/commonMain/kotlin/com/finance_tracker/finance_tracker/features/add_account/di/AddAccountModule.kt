package com.finance_tracker.finance_tracker.features.add_account.di

import com.finance_tracker.finance_tracker.features.add_account.AddAccountViewModel
import com.finance_tracker.finance_tracker.features.add_account.analytics.AddAccountAnalytics
import com.github.murzagalin.evaluator.Evaluator
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val addAccountModule = module {
    factoryOf(::AddAccountViewModel)
    factoryOf(::AddAccountAnalytics)
    single { Evaluator() }
}