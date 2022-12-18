package com.finance_tracker.finance_tracker.presentation.detail_account.di

import com.finance_tracker.finance_tracker.presentation.detail_account.DetailAccountViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val detailAccountModule = module {
    factoryOf(::DetailAccountViewModel)
}