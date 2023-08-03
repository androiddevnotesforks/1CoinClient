package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.suspendTransaction
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.domain.models.Currency
import kotlinx.coroutines.flow.Flow

class AccountsInteractor(
    private val appDatabase: AppDatabase,
    private val accountsRepository: AccountsRepository,
    private val transactionsRepository: TransactionsRepository
) {

    fun getAccountsCountFlow(): Flow<Int> {
        return accountsRepository.getAccountsCountFlow()
    }

    suspend fun deleteAccountById(id: Long) {
        appDatabase.suspendTransaction {
            transactionsRepository.deleteTransactionsByAccountId(id)
            accountsRepository.deleteAccountById(id)
        }
    }

    suspend fun insertAccount(
        accountName: String,
        type: Account.Type,
        balance: Double,
        colorId: Int,
        currency: Currency
    ) {
        accountsRepository.insertAccount(accountName, type, balance, colorId, currency)
    }

    fun getAllAccountColors(): List<AccountColorModel> {
        return accountsRepository.getAllAccountColors()
    }

    suspend fun getAllAccountsFromDatabase(): List<Account> {
       return accountsRepository.getAllAccountsFromDatabase()
    }

    suspend fun updateAccount(
        type: Account.Type,
        name: String,
        balance: Double,
        colorId: Int,
        currency: Currency,
        id: Long
    ) {
        accountsRepository.updateAccount(type, name, balance, colorId, currency, id)
    }

    fun getAccountByIdFlow(id: Long): Flow<Account?> {
        return accountsRepository.getAccountByIdFlow(id)
    }

    suspend fun isAccountNotExists(id: Long): Boolean {
        return accountsRepository.isAccountNotExists(id)
    }

    suspend fun updateAccountPosition(position: Int, accountId: Long) {
        accountsRepository.updateAccountPosition(position, accountId)
    }
}