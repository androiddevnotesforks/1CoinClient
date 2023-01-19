package com.finance_tracker.finance_tracker.presentation.common.formatters

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.common.math.negativeSignOrEmpty
import com.finance_tracker.finance_tracker.domain.models.Amount
import kotlin.math.absoluteValue

enum class AmountFormatMode {
    NegativeSign,
    NoSigns
}

enum class ReductionMode {
    Soft,
    Hard
}

@Composable
internal fun Amount.format(
    mode: AmountFormatMode = AmountFormatMode.NoSigns,
    reductionMode: ReductionMode = ReductionMode.Soft
): String {
    val formattedCurrencyNumber = formatAmount(
        number = amountValue.absoluteValue,
        currencyCode = currency.code,
        reductionMode = reductionMode
    ).replace(currency.code, currency.symbol)

    return when (mode) {
        AmountFormatMode.NegativeSign -> {
            amountValue.negativeSignOrEmpty + formattedCurrencyNumber
        }
        AmountFormatMode.NoSigns -> {
            formattedCurrencyNumber
        }
    }
}

@Composable
expect fun formatAmount(
    number: Double,
    currencyCode: String,
    reductionMode: ReductionMode
): String

@Composable
expect fun isCurrencyPositionAtStart(): Boolean