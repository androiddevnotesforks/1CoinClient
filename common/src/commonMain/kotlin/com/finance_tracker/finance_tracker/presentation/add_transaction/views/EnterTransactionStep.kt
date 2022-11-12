package com.finance_tracker.finance_tracker.presentation.add_transaction.views

import com.finance_tracker.finance_tracker.core.common.Parcelable
import com.finance_tracker.finance_tracker.core.common.Parcelize

@Parcelize
enum class EnterTransactionStep(val textId: String? = null): Parcelable {
    Account("add_transaction_stage_account"),
    Category("add_transaction_stage_category"),
    Amount;

    fun previous(): EnterTransactionStep {
        val steps = values()
        val previousIndex = (ordinal - 1).coerceAtLeast(0)
        return steps[previousIndex]
    }

    fun next(): EnterTransactionStep {
        val steps = values()
        val nextIndex = (ordinal + 1).coerceAtMost(steps.size - 1)
        return steps[nextIndex]
    }
}