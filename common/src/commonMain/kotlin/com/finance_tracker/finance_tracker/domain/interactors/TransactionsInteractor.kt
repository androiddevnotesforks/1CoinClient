package com.finance_tracker.finance_tracker.domain.interactors

import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import kotlinx.datetime.Month
import java.util.Calendar
import java.util.Date
import kotlin.math.pow

class TransactionsInteractor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
) {

    @Suppress("MagicNumber")
    private val minPercentValue = MIN_PERCENT_PRECISION / 10f.pow(MIN_PERCENT_PRECISION)

    suspend fun getTransactions(accountId: Long? = null): List<TransactionListModel> {
        val allTransactions = if (accountId == null) {
            transactionsRepository.getAllTransactions()
        } else {
            transactionsRepository.getTransactions(accountId)
        }
        val newTransactions = mutableListOf<TransactionListModel>()
        for (transaction in allTransactions) {
            val lastUiTransactionModel = newTransactions.lastOrNull()

            val isTransactionForNextDay = lastUiTransactionModel is TransactionListModel.Data &&
                    !lastUiTransactionModel.transaction.date.isCalendarDateEquals(transaction.date)
            if (lastUiTransactionModel == null || isTransactionForNextDay) {
                val totalIncomeAmount =
                    transactionsRepository.getTotalTransactionAmountByDateAndType(
                        date = transaction.date,
                        type = TransactionType.Income
                    )
                val totalExpenseAmount =
                    transactionsRepository.getTotalTransactionAmountByDateAndType(
                        date = transaction.date,
                        type = TransactionType.Expense
                    )
                newTransactions += TransactionListModel.DateAndDayTotal(
                    date = transaction.date,
                    income = totalIncomeAmount,
                    expense = totalExpenseAmount
                ) // получение общего дохода и расхода за 1 день
            }
            newTransactions += TransactionListModel.Data(transaction)
        }
        return newTransactions
    }

    private suspend fun updateAccountBalanceForDeleteTransaction(transaction: Transaction) {
        if (transaction.type == TransactionType.Expense) {
            accountsRepository.increaseAccountBalance(transaction.account.id, transaction.amount)
        } else {
            accountsRepository.reduceAccountBalance(transaction.account.id, transaction.amount)
        }
    }

    private suspend fun updateAccountBalanceForAddTransaction(transaction: Transaction) {
        if (transaction.type == TransactionType.Expense) {
            accountsRepository.reduceAccountBalance(transaction.account.id, transaction.amount)
        } else {
            accountsRepository.increaseAccountBalance(transaction.account.id, transaction.amount)
        }
    }

    suspend fun deleteTransactions(transactions: List<Transaction>) {
        transactionsRepository.deleteTransactions(transactions)

        transactions.forEach {
            updateAccountBalanceForDeleteTransaction(it)
        }
    }

    suspend fun addOrUpdateTransaction(transaction: Transaction) {
        transactionsRepository.addOrUpdateTransaction(transaction)
        updateAccountBalanceForAddTransaction(transaction)
    }

    private fun Date?.isCalendarDateEquals(date: Date?): Boolean {
        if (this == date) return true
        if (this == null) return false
        if (date == null) return false

        val calendar1 = Calendar.getInstance().apply { time = this@isCalendarDateEquals }
        val calendar2 = Calendar.getInstance().apply { time = date }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }

    @Suppress("MagicNumber")
    suspend fun getTransactions(
        transactionType: TransactionType, month: Month
    ): TxsByCategoryChart {
        val transactions = transactionsRepository.getTransactions(transactionType, month)
        val totalAmount = transactions.sumOf { it.amount }

        val rawCategoryPieces = transactions
            .groupBy { it.category }
            .mapValues { (_, amounts) -> amounts.sumOf { it.amount } }
            .map { (category, amount) ->
                TxsByCategoryChart.Piece(
                    category = category,
                    amount = amount,
                    color = Color(
                        red = (0..255).random().toFloat() / 255f,
                        green = (0..255).random().toFloat() / 255f,
                        blue = (0..255).random().toFloat() / 255f
                    )
                )
            }

        val categoryPieces = rawCategoryPieces
            .filter {
                it.amount / totalAmount >= minPercentValue
            }

        val otherPieces = rawCategoryPieces - categoryPieces.toSet()
        val allCategoryPieces = if (otherPieces.sumOf { it.amount } >= minPercentValue) {
            categoryPieces + otherPieces
        } else {
            categoryPieces
        }
            .sortedBy { (_, amount) -> amount }

        return TxsByCategoryChart(
            pieces = allCategoryPieces,
            total = totalAmount
        )
    }

    companion object {
        private const val MIN_PERCENT_PRECISION = 1
    }
}