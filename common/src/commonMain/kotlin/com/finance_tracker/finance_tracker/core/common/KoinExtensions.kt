package com.finance_tracker.finance_tracker.core.common

import com.arkivanov.decompose.ComponentContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.mp.KoinPlatformTools

@Suppress("UnusedReceiverParameter")
inline fun <reified T> ComponentContext.inject(noinline parameters: ParametersDefinition? = null): T {
    return globalKoin.get(parameters = parameters)
}

val globalKoin by lazy {
    KoinPlatformTools.defaultContext().get()
}
