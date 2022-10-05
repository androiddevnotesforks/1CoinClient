package com.finance_tracker.finance_tracker.data.repositories

import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.financetracker.financetracker.TransactionsEntityQueries
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
class TransactionsRepository(
    private val transactionsEntityQueries: TransactionsEntityQueries
) {
    fun getAllTransactions(): List<Transaction> {
        return transactionsEntityQueries.getAllTransactionsWithAccounts {
                id, type, amount, amountCurrency,
                category, accountId, date,
                _, accountName, balance ->
            Transaction(
                type = type,
                amountCurrency = amountCurrency,
                account = Account(
                    id = accountId ?: 0L,
                    type = Account.Type.Cash,
                    color = Color.Red,
                    name = accountName
                ),
                amount = amount,
                category = category,
                cardNumber = null,
                date = Date(date)
            )
        }.executeAsList()
    }
}