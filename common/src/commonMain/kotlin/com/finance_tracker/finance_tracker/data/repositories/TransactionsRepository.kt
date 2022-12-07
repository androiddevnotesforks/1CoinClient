package com.finance_tracker.finance_tracker.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.finance_tracker.finance_tracker.core.common.hexToColor
import com.finance_tracker.finance_tracker.data.data_sources.TransactionSource
import com.finance_tracker.finance_tracker.data.data_sources.TransactionSourceFactory
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.financetracker.financetracker.data.TransactionsEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Month
import java.util.Date

private const val PageSize = 20

class TransactionsRepository(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    private val transactionSourceFactory: TransactionSourceFactory,
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
        colorHex: String,
        currency: String,
        id__: Long,
        name_: String,
        icon: String,
        position: Long?,
        isExpense: Boolean,
        isIncome: Boolean
    ) -> Transaction = { id, type, amount, amountCurrency, categoryId,
                         accountId, _, date, _, accountType, accountName, balance, accountColorHex, _,
                         _, categoryName, categoryIcon, _, _, _ ->
        val currency = Currency.getByCode(amountCurrency)
        Transaction(
            id = id,
            type = type,
            account = Account(
                id = accountId ?: 0L,
                type = accountType,
                color = accountColorHex.hexToColor(),
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

    private val paginatedTransactions: Flow<PagingData<Transaction>> =
        Pager(PagingConfig(pageSize = PageSize)) {
            TransactionSource(transactionsEntityQueries)
        }.flow

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

    fun getPaginatedTransactions(): Flow<PagingData<Transaction>> {
        return paginatedTransactions
    }

    fun getPaginatedTransactionsByAccountId(id: Long): Flow<PagingData<Transaction>> {
        return Pager(PagingConfig(pageSize = PageSize)) {
            transactionSourceFactory.create(id)
        }.flow
    }
}