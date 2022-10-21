package com.finance_tracker.finance_tracker.presentation.add_account

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.getLocalizedString

sealed class AddAccountEvent {
    object Close: AddAccountEvent()
    data class ShowToast(
        val textId: String
    ): AddAccountEvent()
}

fun handleEvent(
    event: AddAccountEvent,
    context: Context
) {
    when (event) {
        AddAccountEvent.Close -> {
            // TODO: Вернуть код
            //navigator.popBackStack()
        }
        is AddAccountEvent.ShowToast -> {
            // TODO: Вернуть код
            val string = getLocalizedString(event.textId, context)
            println(string)
            //Toast.makeText(context, event.textId, Toast.LENGTH_SHORT).show()
        }
    }
}