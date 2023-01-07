package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.database.mappers.accountToDomainModel
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.financetracker.financetracker.data.AccountsEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AccountsRepository(
    private val accountsEntityQueries: AccountsEntityQueries
) {

    suspend fun insertAccount(
        accountName: String,
        type: Account.Type,
        balance: Double,
        colorId: Int,
        currency: Currency
    ) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.insertAccount(
                id = null,
                type = type,
                name = accountName,
                balance = balance,
                colorId = colorId,
                currency = currency.code
            )
        }
    }

    fun getAllAccountColors(): List<AccountColorModel> {
        return AccountColorModel.values().toList()
    }

    suspend fun getAllAccountsFromDatabase(): List<Account> {
        return withContext(Dispatchers.IO) {
            accountsEntityQueries.getAllAccounts().executeAsList()
                .map { it.accountToDomainModel() }
        }
    }

    suspend fun increaseAccountBalance(id: Long, value: Double) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.increaseBalanceByAccountId(value, id)
        }
    }

    suspend fun updateAccount(
        type: Account.Type,
        name: String,
        balance: Double,
        colorId: Int,
        currency: Currency,
        id: Long
    ) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.updateAccountById(
                type = type,
                name = name,
                balance = balance,
                colorId = colorId,
                currency = currency.code,
                id = id,
            )
        }
    }

    fun getAccountByIdFlow(id: Long): Flow<Account> {
        return accountsEntityQueries.getAccountById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map {
                it?.accountToDomainModel() ?: Account.EMPTY
            }
    }

    suspend fun deleteAccountById(id: Long) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.deleteAccountById(id)
        }
    }
}