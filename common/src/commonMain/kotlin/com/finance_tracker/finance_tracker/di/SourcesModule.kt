package com.finance_tracker.finance_tracker.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import com.finance_tracker.finance_tracker.data.data_sources.TransactionSource
import com.finance_tracker.finance_tracker.data.data_sources.TransactionSourceFactory

internal val sourcesModule = module {
    factoryOf(::TransactionSource)
    factoryOf(::TransactionSourceFactory)
}