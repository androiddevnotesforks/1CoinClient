package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.financetracker.financetracker.data.TransactionsEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Month
import java.util.Date

class TransactionsRepository(
    private val transactionsEntityQueries: TransactionsEntityQueries
) {

    private val fullTransactionMapper: (
        id: Long,
        type: TransactionType,
        amount: Double,
        amountCurrency: String,
        categoryId: Long?,
        accountId: Long?,
        insertionDate: Date,
        date: Date,
        id_: Long,
        type_: Account.Type,
        name: String,
        balance: Double,
        colorId: Int,
        currency: String,
        id__: Long,
        name_: String,
        icon: String,
        position: Long?,
        isExpense: Boolean,
        isIncome: Boolean
    ) -> Transaction = { id, type, amount, amountCurrency, categoryId,
                         accountId, _, date, _, accountType, accountName, balance, accountColorId, _,
                         _, categoryName, categoryIcon, _, _, _ ->
        val currency = Currency.getByCode(amountCurrency)
        Transaction(
            id = id,
            type = type,
            account = Account(
                id = accountId ?: 0L,
                type = accountType,
                colorModel = AccountColorModel.from(accountColorId),
                name = accountName,
                balance = Amount(
                    amountValue = balance,
                    currency = currency
                )
            ),
            category = categoryId?.let {
                Category(
                    id = categoryId,
                    name = categoryName,
                    iconId = categoryIcon
                )
            },
            amount = Amount(
                currency = currency,
                amountValue = amount
            ),
            date = date
        )
    }

    suspend fun getAllTransactions(): List<Transaction> {
        return withContext(Dispatchers.IO) {
            transactionsEntityQueries.getAllFullTransactions(fullTransactionMapper).executeAsList()
        }
    }

    suspend fun getTransactions(accountId: Long): List<Transaction> {
        return withContext(Dispatchers.IO) {
            transactionsEntityQueries.getFullTransactionsByAccountId(accountId, fullTransactionMapper).executeAsList()
        }
    }

    suspend fun getTransactions(transactionType: TransactionType, month: Month): List<Transaction> {
        return withContext(Dispatchers.IO) {
            transactionsEntityQueries.getFullTransactions(
                transactionType = transactionType,
                monthNumber = String.format(format = "%02d", month.value),
                mapper = fullTransactionMapper
            ).executeAsList()
        }
    }

    suspend fun deleteTransactions(transactions: List<Transaction>) {
        withContext(Dispatchers.IO) {
            transactionsEntityQueries.deleteTransactionsById(
                ids = transactions.mapNotNull { it.id }
            )
        }
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            val transactionId = transaction.id ?: return@withContext
            transactionsEntityQueries.deleteTransactionById(transactionId)
        }
    }

    suspend fun addOrUpdateTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            transactionsEntityQueries.insertTransaction(
                id = transaction.id,
                type = transaction.type,
                amount = transaction.amount.amountValue,
                amountCurrency = transaction.amount.currency.code,
                categoryId = transaction.category?.id,
                accountId = transaction.account.id,
                insertionDate = Date(),
                date = transaction.date,
            )
        }
    }
}