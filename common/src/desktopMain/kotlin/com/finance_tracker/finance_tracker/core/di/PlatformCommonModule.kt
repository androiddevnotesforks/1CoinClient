package com.finance_tracker.finance_tracker.core.di

import com.finance_tracker.finance_tracker.core.common.UniqueDeviceIdProvider
import com.finance_tracker.finance_tracker.data.repositories.export_import.FileReader
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual val platformCommonModule: Module = module {
    factoryOf(::UniqueDeviceIdProvider)
    factoryOf(::FileReader)
}
