package com.finance_tracker.finance_tracker.features.plans.set_limit

import com.finance_tracker.finance_tracker.core.common.TextFieldValue
import com.finance_tracker.finance_tracker.core.common.TextRange
import com.finance_tracker.finance_tracker.domain.models.Currency

data class SetLimitState(
    val balance: TextFieldValue,
    val primaryCurrency: Currency
) {
    companion object {
        fun createDefault(): SetLimitState {
            return SetLimitState(
                balance = TextFieldValue(
                    text = "0",
                    selection = TextRange.Zero
                ),
                primaryCurrency = Currency.default
            )
        }
    }
}
