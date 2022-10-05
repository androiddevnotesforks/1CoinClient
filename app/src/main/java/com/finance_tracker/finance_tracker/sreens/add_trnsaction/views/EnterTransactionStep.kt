package com.finance_tracker.finance_tracker.sreens.add_trnsaction.views

import androidx.annotation.StringRes
import com.finance_tracker.finance_tracker.R

enum class EnterTransactionStep(@StringRes val textRes: Int? = null) {
    Account(R.string.add_transaction_stage_account),
    Category(R.string.add_transaction_stage_category),
    Amount;

    fun previous(): EnterTransactionStep {
        val steps = values()
        return steps[ordinal - 1]
    }

    fun next(): EnterTransactionStep {
        val steps = values()
        return steps[ordinal + 1]
    }
}