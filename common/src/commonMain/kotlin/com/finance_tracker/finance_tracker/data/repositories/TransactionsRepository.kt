package com.finance_tracker.finance_tracker.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSource
import com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSourceFactory
import com.finance_tracker.finance_tracker.data.mappers.fullTransactionMapper
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.financetracker.financetracker.data.TransactionsEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.Date

private const val PageSize = 20

class TransactionsRepository(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    private val transactionsPagingSourceFactory: TransactionsPagingSourceFactory,
) {

    private val paginatedTransactions: Flow<PagingData<Transaction>> =
        Pager(PagingConfig(pageSize = PageSize)) {
            TransactionsPagingSource(transactionsEntityQueries)
        }.flow

    suspend fun getTransactions(transactionType: TransactionType, yearMonth: YearMonth): List<Transaction> {
        return withContext(Dispatchers.IO) {
            transactionsEntityQueries.getFullTransactions(
                transactionType = transactionType,
                monthNumber = String.format(format = "%02d", yearMonth.month.value),
                year = yearMonth.year.toString(),
                mapper = fullTransactionMapper
            ).executeAsList()
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
                insertionDate = transaction.insertionDate ?: Date(),
                date = transaction.date,
            )
        }
    }

    fun getPaginatedTransactions(): Flow<PagingData<Transaction>> {
        return paginatedTransactions
    }

    fun getPaginatedTransactionsByAccountId(id: Long): Flow<PagingData<Transaction>> {
        return Pager(PagingConfig(pageSize = PageSize)) {
            transactionsPagingSourceFactory.create(id)
        }.flow
    }

    fun getLastTransactions(limit: Long) : Flow<List<Transaction>> {
        return transactionsEntityQueries.getLastTransactions(
            limit = limit,
            mapper = fullTransactionMapper
        )
            .asFlow()
            .mapToList()
            .flowOn(Dispatchers.IO)
    }
}