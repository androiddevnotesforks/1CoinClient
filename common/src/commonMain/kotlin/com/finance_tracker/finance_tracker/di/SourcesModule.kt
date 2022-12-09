package com.finance_tracker.finance_tracker.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSource
import com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSourceFactory

internal val sourcesModule = module {
    factoryOf(::TransactionsPagingSource)
    factoryOf(::TransactionsPagingSourceFactory)
}