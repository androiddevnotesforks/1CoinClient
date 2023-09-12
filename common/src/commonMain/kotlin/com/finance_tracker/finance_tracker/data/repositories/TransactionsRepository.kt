package com.finance_tracker.finance_tracker.data.repositories

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.finance_tracker.finance_tracker.core.common.date.currentLocalDateTime
import com.finance_tracker.finance_tracker.core.common.date.models.YearMonth
import com.finance_tracker.finance_tracker.core.common.zeroPrefixed
import com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSource
import com.finance_tracker.finance_tracker.data.data_sources.TransactionsPagingSourceFactory
import com.finance_tracker.finance_tracker.data.database.mappers.fullTransactionMapper
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.financetracker.financetracker.data.TransactionsEntityQueries
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrDefault
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.number

private const val PageSize = 15

class TransactionsRepository(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    private val transactionsPagingSourceFactory: TransactionsPagingSourceFactory,
) {

    private val paginatedTransactions: Flow<PagingData<Transaction>> =
        Pager(PagingConfig(pageSize = PageSize)) {
            TransactionsPagingSource(transactionsEntityQueries)
        }.flow
            .flowOn(Dispatchers.IO)

    fun getTransactionsFlow(
        transactionType: TransactionType,
        yearMonth: YearMonth
    ): Flow<List<Transaction>> {
        return getTransactionsQuery(transactionType, yearMonth)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .flowOn(Dispatchers.IO)
    }

    suspend fun getTransactions(transactionType: TransactionType, yearMonth: YearMonth): List<Transaction> {
        return withContext(Dispatchers.IO) {
            getTransactionsQuery(transactionType, yearMonth)
                .executeAsList()
        }
    }

    private fun getTransactionsQuery(
        transactionType: TransactionType,
        yearMonth: YearMonth
    ): Query<Transaction> {
        return transactionsEntityQueries.getFullTransactions(
            transactionType = transactionType,
            monthNumber = yearMonth.month.number.zeroPrefixed(2),
            year = yearMonth.year.toString(),
            mapper = fullTransactionMapper
        )
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            val transactionId = transaction.id ?: return@withContext
            transactionsEntityQueries.deleteTransactionById(transactionId)
        }
    }

    suspend fun deleteTransactionsByAccountId(accountId: Long) {
        withContext(Dispatchers.IO) {
            transactionsEntityQueries.deleteTransactionsByAccountId(accountId)
        }
    }

    suspend fun deleteCategoryForTransactionsByCategoryId(categoryId: Long) {
        withContext(Dispatchers.IO) {
            transactionsEntityQueries.deleteCategoryForTransactionsByCategoryId(categoryId)
        }
    }

    suspend fun addOrUpdateTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            transactionsEntityQueries.insertTransaction(
                id = transaction.id,
                type = transaction.type,
                amount = transaction.primaryAmount.amountValue,
                amountCurrency = transaction.primaryAmount.currency.code,
                categoryId = transaction._category?.id,
                accountId = transaction.primaryAccount.id,
                insertionDate = transaction.insertionDateTime ?: Clock.System.currentLocalDateTime(),
                date = transaction.dateTime,
                secondaryAmount = transaction.secondaryAmount?.amountValue,
                secondaryAmountCurrency = transaction.secondaryAmount?.currency?.code,
                secondaryAccountId = transaction.secondaryAccount?.id
            )
        }
    }

    fun getPaginatedTransactions(): Flow<PagingData<Transaction>> {
        return paginatedTransactions
    }

    fun getTransactionsSizeUpdates(): Flow<Long> {
        return transactionsEntityQueries.getAllTransactionsCount()
            .asFlow()
            .mapToOneOrDefault(0L)
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
    }

    fun getPaginatedTransactionsByAccountId(id: Long): Flow<PagingData<Transaction>> {
        return Pager(PagingConfig(pageSize = PageSize)) {
            transactionsPagingSourceFactory.create(id)
        }.flow
            .flowOn(Dispatchers.IO)
    }

    fun getTransactionsByAccountSizeUpdates(id: Long): Flow<Long> {
        return transactionsEntityQueries.getAllTransactionsByAccountIdCount(id)
            .asFlow()
            .mapToOneOrDefault(0L)
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
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