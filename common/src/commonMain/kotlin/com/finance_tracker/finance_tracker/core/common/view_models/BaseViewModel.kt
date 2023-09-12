package com.finance_tracker.finance_tracker.core.common.view_models

import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<Action : Any>: KViewModel() {

    val screenKey: String = this::class.simpleName.orEmpty()

    private val _viewActions = Channel<Action?>(
        capacity = Channel.Factory.UNLIMITED
    )

    protected var viewAction: Action?
        get() = null
        set(value) {
            _viewActions.trySend(value)
        }

    val viewActionChannel = _viewActions.receiveAsFlow()
    fun viewActions(): WrappedFlow<Action?> = WrappedFlow(viewActionChannel)
}