package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class SystemBarsConfig(
    initIsLight: Boolean = true
) {
    var isLight: Boolean by mutableStateOf(initIsLight)

    fun copy() = SystemBarsConfig(
        initIsLight = isLight
    )
}

@Composable
fun UpdateSystemBarsConfigEffect(action: SystemBarsConfig.() -> Unit) {
    val currentSystemBarsConfig = LocalSystemBarsConfig.current
    val oldSystemBarsConfig = remember { currentSystemBarsConfig.copy() }
    DisposableEffect(Unit) {
        action.invoke(currentSystemBarsConfig)
        onDispose {
            currentSystemBarsConfig.isLight = oldSystemBarsConfig.isLight
        }
    }
}

val LocalSystemBarsConfig = compositionLocalOf { SystemBarsConfig() }