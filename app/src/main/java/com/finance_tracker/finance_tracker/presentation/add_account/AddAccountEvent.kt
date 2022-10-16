package com.finance_tracker.finance_tracker.presentation.add_account

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed class AddAccountEvent {
    object Close: AddAccountEvent()
    data class ShowToast(
        @StringRes val text: Int
    ): AddAccountEvent()
}

fun handleEvent(
    event: AddAccountEvent,
    context: Context,
    navigator: DestinationsNavigator
) {
    when (event) {
        AddAccountEvent.Close -> navigator.popBackStack()
        is AddAccountEvent.ShowToast -> {
            Toast.makeText(context, event.text, Toast.LENGTH_SHORT).show()
        }
    }
}