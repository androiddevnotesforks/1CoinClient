package com.finance_tracker.finance_tracker.presentation.common.formatters

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import com.finance_tracker.finance_tracker.core.common.NumberFormatters
import com.finance_tracker.finance_tracker.core.common.locale.toJavaLocale
import com.finance_tracker.finance_tracker.core.common.math.negativeSignOrEmpty
import com.finance_tracker.finance_tracker.domain.models.Amount
import kotlin.math.absoluteValue

enum class AmountFormatMode {
    NegativeSign,
    NoSigns
}

@Composable
fun Amount.format(
    mode: AmountFormatMode = AmountFormatMode.NoSigns
): String {
    val locale = Locale.current.toJavaLocale()
    val formatter = NumberFormatters.AmountFormat(locale).apply {
        currencyCode = currency.code
    }

    val formattedCurrencyNumber = formatter.format(amountValue.absoluteValue)
        .replace(currency.code, currency.symbol)

    return when (mode) {
        AmountFormatMode.NegativeSign -> {
            amountValue.negativeSignOrEmpty + formattedCurrencyNumber
        }
        AmountFormatMode.NoSigns -> {
            formattedCurrencyNumber
        }
    }
}