package com.finance_tracker.finance_tracker.core.common

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.subscribe
import com.finance_tracker.finance_tracker.core.common.view_models.ComponentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.parameter.ParametersDefinition

abstract class BaseComponent<VM: ComponentViewModel<*, *>>(
    componentContext: ComponentContext
): ComponentContext by componentContext {

    abstract val viewModel: VM

    private var coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var handleComponentActionJob: Job? = null

    protected fun <Action: Any> ComponentViewModel<*, Action>.handleComponentAction(
        block: (Action) -> Unit
    ) {
        lifecycle.subscribe(
            onStart = {
                handleComponentActionJob = componentActionChannel
                    .onEach { block(it) }
                    .launchIn(coroutineScope)
            },
            onStop = {
                handleComponentActionJob?.cancel()
                handleComponentActionJob = null
            }
        )
    }
}

inline fun <reified VM: ComponentViewModel<*, *>> BaseComponent<VM>.injectViewModel(
    noinline parameters: ParametersDefinition? = null
): VM {
    return instanceKeeper.getOrCreate { globalKoin.get(parameters = parameters) }
}