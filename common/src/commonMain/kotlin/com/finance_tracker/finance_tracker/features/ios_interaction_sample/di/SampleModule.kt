package com.finance_tracker.finance_tracker.features.ios_interaction_sample.di

import com.finance_tracker.finance_tracker.features.ios_interaction_sample.SampleViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val sampleModule = module {
    factoryOf(::SampleViewModel)
}