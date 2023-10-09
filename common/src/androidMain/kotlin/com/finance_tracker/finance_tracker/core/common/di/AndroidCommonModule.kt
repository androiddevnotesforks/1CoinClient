package com.finance_tracker.finance_tracker.core.common.di

import com.finance_tracker.finance_tracker.core.common.UniqueDeviceIdProvider
import com.finance_tracker.finance_tracker.data.repositories.export_import.FileReader
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val androidCommonModule = module {
    factoryOf(::UniqueDeviceIdProvider)
    factoryOf(::FileReader)
}