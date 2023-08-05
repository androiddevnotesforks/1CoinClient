package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat

@ReadOnlyComposable
@Composable
actual fun getLocaleLanguage(): String {
    val configuration = LocalConfiguration.current
    val locale = ConfigurationCompat.getLocales(configuration).get(0) ?: LocaleListCompat.getDefault()[0]!!
    return locale.language
}