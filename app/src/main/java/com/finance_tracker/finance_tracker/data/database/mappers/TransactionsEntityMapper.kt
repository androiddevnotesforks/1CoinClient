package com.finance_tracker.finance_tracker.data.database.mappers

import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.financetracker.financetracker.TransactionsEntity
import java.util.Date

fun TransactionsEntity.toDomainModel(): Transaction {
    return Transaction(
        type = type,
        amountCurrency = amountCurrency,
        account = Account(
            id = 1,
            type = Account.Type.DebitCard,
            name = "123",
            color = Color.Red
        ),
        amount = amount,
        category = category,
        cardNumber = null,
        date = Date(date)
    )
}