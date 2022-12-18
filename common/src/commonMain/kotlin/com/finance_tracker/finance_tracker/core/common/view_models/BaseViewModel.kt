package com.finance_tracker.finance_tracker.core.common.view_models

import com.adeo.kviewmodel.BaseSharedViewModel

abstract class BaseViewModel<Action>: BaseSharedViewModel<Unit, Action, Unit>(
    initialState = Unit
) {
    override fun obtainEvent(viewEvent: Unit) = Unit
}