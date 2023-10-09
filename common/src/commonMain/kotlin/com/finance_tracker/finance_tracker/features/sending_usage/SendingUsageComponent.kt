package com.finance_tracker.finance_tracker.features.sending_usage

import com.arkivanov.decompose.ComponentContext

class SendingUsageComponent(
    componentContext: ComponentContext,
    private val close: () -> Unit,
) : ComponentContext by componentContext {

    fun onOkClick() {
        close()
    }
}