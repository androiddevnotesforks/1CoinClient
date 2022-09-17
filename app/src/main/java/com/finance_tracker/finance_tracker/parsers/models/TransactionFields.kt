package com.finance_tracker.finance_tracker.parsers.models

import com.finance_tracker.finance_tracker.data.models.Transaction

typealias TransactionFields = Map<TransactionField, String>

fun TransactionFields.getAmount(): Double {
    return this[TransactionField.Amount]?.toDoubleOrNull() ?: 0.0
}

fun TransactionFields.getAmountCurrency(): String {
    return this[TransactionField.AmountCurrency].toString()
}

fun TransactionFields.getCardNumber(): String? {
    return this[TransactionField.CardNumber]
}

fun TransactionFields.getTransactionType(): Transaction.Type? {
    return when (this[TransactionField.Type]) {
        "Снятие", "Покупка" -> Transaction.Type.Expense
        "Пополнение" -> Transaction.Type.Income
        "Перевод" -> Transaction.Type.Transfer
        else -> null
    }
}