package com.finance_tracker.finance_tracker.core.common.view_models

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ComponentViewModel<ViewAction : Any, ComponentAction : Any> : InstanceKeeper.Instance {

    protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    val screenKey: String = this::class.simpleName.orEmpty()

    // Component actions

    private val _componentActions = Channel<ComponentAction?>(
        capacity = Channel.Factory.UNLIMITED
    )
    protected var componentAction: ComponentAction?
        get() = null
        set(value) {
            _componentActions.trySend(value)
        }
    val componentActionChannel = _componentActions.receiveAsFlow()
        .filterNotNull()

    // View actions

    private val _viewActions = Channel<ViewAction?>(
        capacity = Channel.Factory.UNLIMITED
    )

    protected var viewAction: ViewAction?
        get() = null
        set(value) {
            _viewActions.trySend(value)
        }

    val viewActionChannel = _viewActions.receiveAsFlow()

    override fun onDestroy() {
        viewModelScope.cancel()
    }
}

abstract class BaseViewModel<Action : Any>: ComponentViewModel<Action, Unit>()