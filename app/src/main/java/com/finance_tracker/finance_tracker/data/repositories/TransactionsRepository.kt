package com.finance_tracker.finance_tracker.data.repositories

import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.core.common.DateFormatType
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.financetracker.financetracker.TransactionsEntityQueries
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
class TransactionsRepository(
    private val transactionsEntityQueries: TransactionsEntityQueries
) {

    fun getAllTransactions(): List<Transaction> {
        return transactionsEntityQueries.getAllFullTransactions {
                id, type, amount, amountCurrency, categoryId,
                accountId, date, _, accountName, _,
                _, categoryName, categoryIcon ->
            Transaction(
                id = id,
                type = type,
                amountCurrency = amountCurrency,
                account = Account(
                    id = accountId ?: 0L,
                    type = Account.Type.Cash,
                    color = Color.Red,
                    name = accountName
                ),
                category = categoryId?.let {
                    Category(
                        id = categoryId,
                        name = categoryName,
                        icon = categoryIcon
                    )
                },
                amount = amount,
                date = date
            )
        }.executeAsList()
    }

    fun getTotalTransactionAmountByDateAndType(
        date: Date,
        type: TransactionType
    ): Double {
        return transactionsEntityQueries.getTotalTransactionAmountByDateAndType(
            date = DateFormatType.CommonDateFormat.format(date),
            type = type
        ).executeAsOneOrNull()?.SUM ?: 0.0
    }
}