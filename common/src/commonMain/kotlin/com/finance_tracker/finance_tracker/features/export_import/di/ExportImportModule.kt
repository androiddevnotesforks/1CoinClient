package com.finance_tracker.finance_tracker.features.export_import.di

import com.finance_tracker.finance_tracker.features.export_import.export.ExportViewModel
import com.finance_tracker.finance_tracker.features.export_import.export.analytics.ExportAnalytics
import com.finance_tracker.finance_tracker.features.export_import.import.ImportViewModel
import com.finance_tracker.finance_tracker.features.export_import.import.analytics.ImportAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val exportImportModule = module {
    factoryOf(::ExportViewModel)
    factoryOf(::ExportAnalytics)
    factoryOf(::ImportViewModel)
    factoryOf(::ImportAnalytics)
}