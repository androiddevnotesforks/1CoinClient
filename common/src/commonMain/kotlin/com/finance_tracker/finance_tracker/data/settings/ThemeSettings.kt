package com.finance_tracker.finance_tracker.data.settings

import com.finance_tracker.finance_tracker.core.common.stateIn
import com.finance_tracker.finance_tracker.domain.models.ThemeMode
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalSettingsApi::class)
class ThemeSettings(factory: Settings.Factory) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val settings: ObservableSettings = factory.create("theme") as ObservableSettings
    private val flowSettings: FlowSettings = settings.toFlowSettings(Dispatchers.IO)
    val themeMode = flowSettings
        .getStringOrNullFlow(KEY_THEME_MODE)
        .map(ThemeMode::from)
        .map { it ?: ThemeMode.System }
        .stateIn(coroutineScope, initialValue = ThemeMode.System)

    fun saveThemeMode(themeMode: ThemeMode) {
        settings.putString(KEY_THEME_MODE, themeMode.modeId)
    }

    companion object {
        private const val KEY_THEME_MODE = "theme_mode"
    }
}