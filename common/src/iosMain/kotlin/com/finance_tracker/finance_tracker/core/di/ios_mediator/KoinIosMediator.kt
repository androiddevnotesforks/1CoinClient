package com.finance_tracker.finance_tracker.core.di.ios_mediator

import com.finance_tracker.finance_tracker.core.common.AppInitializer
import com.finance_tracker.finance_tracker.core.common.EmptyContext
import com.finance_tracker.finance_tracker.core.common.di.Di
import com.finance_tracker.finance_tracker.core.common.logger.LoggerInitializer
import com.finance_tracker.finance_tracker.data.settings.AccountSettings
import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import com.finance_tracker.finance_tracker.data.settings.UserSettings
import com.finance_tracker.finance_tracker.features.home.HomeViewModel
import com.finance_tracker.finance_tracker.features.ios_interaction_sample.SampleViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DiMediator {
    val core = CoreModuleIos()
    val settings = SettingsModuleIos()
    val home = HomeFeatureIos()
    val sample = SampleFeatureIos()

    fun configure() {
        Di.init(EmptyContext)
    }
}

class CoreModuleIos: KoinComponent {
    val appInitializer by inject<AppInitializer>()
    val loggerInitializer by inject<LoggerInitializer>()
}

class SettingsModuleIos: KoinComponent {
    val accountSettings by inject<AccountSettings>()
    val analyticsSettings by inject<AnalyticsSettings>()
    val userSettings by inject<UserSettings>()
}

class HomeFeatureIos: KoinComponent {
    val homeViewModel by inject<HomeViewModel>()
}

class SampleFeatureIos: KoinComponent {
    val sampleViewModel by inject<SampleViewModel>()
}