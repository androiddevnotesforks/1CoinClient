package com.finance_tracker.finance_tracker.domain.interactors.transactions

import app.cash.paging.PagingData
import app.cash.paging.insertSeparators
import app.cash.paging.map
import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.suspendTransaction
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

private const val LastTransactionsLimit = 3L

class TransactionsInteractor(
    private val updateAccountBalanceForTransactionUseCase: UpdateAccountBalanceForTransactionUseCase,
    private val transactionsRepository: TransactionsRepository,
    private val appDatabase: AppDatabase
) {

    fun getLastTransactions(): Flow<List<TransactionListModel.Data>> {
        return transactionsRepository.getLastTransactions(limit = LastTransactionsLimit)
            .map { transactions ->
                transactions.map(TransactionListModel::Data)
            }
    }

    private suspend fun updateAccountBalanceForTransaction(
        oldTransaction: Transaction?,
        newTransaction: Transaction?
    ) {
        return updateAccountBalanceForTransactionUseCase.invoke(oldTransaction, newTransaction)
    }

    suspend fun deleteTransactions(transactions: List<Transaction>) {
        appDatabase.suspendTransaction {
            deleteTransactionsInternal(transactions)
        }
    }

    private suspend fun deleteTransactionsInternal(transactions: List<Transaction>) {
        transactions.forEach {
            deleteTransactionInternal(it)
        }
    }

    suspend fun deleteTransaction(
        transaction: Transaction
    ) {
        appDatabase.suspendTransaction {
            deleteTransactionInternal(transaction)
        }
    }

    private suspend fun deleteTransactionInternal(transaction: Transaction) {
        transactionsRepository.deleteTransaction(transaction)
        updateAccountBalanceForTransaction(
            oldTransaction = transaction,
            newTransaction = null
        )
    }

    suspend fun addTransaction(transaction: Transaction) {
        appDatabase.suspendTransaction {
            transactionsRepository.addOrUpdateTransaction(transaction)
            updateAccountBalanceForTransaction(
                oldTransaction = null,
                newTransaction = transaction
            )
        }
    }

    suspend fun updateTransaction(
        oldTransaction: Transaction,
        newTransaction: Transaction
    ) {
        appDatabase.suspendTransaction {
            transactionsRepository.addOrUpdateTransaction(newTransaction)
            updateAccountBalanceForTransaction(
                oldTransaction = oldTransaction,
                newTransaction = newTransaction
            )
        }
    }

    fun getPaginatedTransactions(): Flow<PagingData<TransactionListModel>> {
        return transactionsRepository.getPaginatedTransactions()
            .map { it.map(TransactionListModel::Data) }
            .map(::insertSeparators)
    }

    fun getTransactionsSizeUpdates(): Flow<Long> {
        return transactionsRepository.getTransactionsSizeUpdates()
    }

    fun getPaginatedTransactionsByAccountId(id: Long): Flow<PagingData<TransactionListModel>> {
        return transactionsRepository.getPaginatedTransactionsByAccountId(id)
            .map { it.map(TransactionListModel::Data) }
            .map(::insertSeparators)
            .flowOn(Dispatchers.IO)
    }

    fun getTransactionsByAccountIdSizeUpdates(id: Long): Flow<Long> {
        return transactionsRepository.getTransactionsByAccountSizeUpdates(id)
    }

    @Suppress("LABEL_NAME_CLASH")
    private fun insertSeparators(
        pagingData: PagingData<TransactionListModel.Data>
    ): PagingData<TransactionListModel> {
        return pagingData.insertSeparators { data: TransactionListModel.Data?,
                                             data2: TransactionListModel.Data? ->
            val previousTransaction = data?.transaction
            val currentTransaction = data2?.transaction ?: return@insertSeparators null
            if (previousTransaction == null ||
                previousTransaction.dateTime.date != currentTransaction.dateTime.date) {
                TransactionListModel.DateAndDayTotal(
                    dateTime = currentTransaction.dateTime,
                )
            } else {
                null
            }
        }
    }
}