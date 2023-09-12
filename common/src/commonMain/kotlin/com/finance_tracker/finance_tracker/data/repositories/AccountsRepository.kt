package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.localizedString
import com.finance_tracker.finance_tracker.data.database.mappers.accountToDomainModel
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorModel
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.financetracker.financetracker.data.AccountsEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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
            val currentMaxPosition: Int? = accountsEntityQueries
                .getAllAccounts()
                .executeAsList()
                .maxOfOrNull { it.position }

            accountsEntityQueries.insertAccount(
                id = null,
                type = type,
                name = accountName,
                balance = balance,
                colorId = colorId,
                currency = currency.code,
                position = currentMaxPosition?.let { it + 1 } ?: 0
            )
        }
    }

    fun getAllAccountColors(): List<AccountColorModel> {
        return AccountColorModel.values().toList()
    }

    suspend fun getAllAccountsFromDatabase(): List<Account> {
        return withContext(Dispatchers.IO) {
            accountsEntityQueries.getAllAccounts().executeAsList()
                .sortedBy { it.position }
                .map { it.accountToDomainModel() }
        }
    }

    suspend fun increaseAccountBalance(id: Long, value: Double) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.increaseBalanceByAccountId(value, id)
        }
    }

    suspend fun decreaseAccountBalance(id: Long, value: Double) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.decreaseBalanceByAccountId(value, id)
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

    fun getAccountByIdFlow(id: Long): Flow<Account?> {
        return accountsEntityQueries.getAccountById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.accountToDomainModel() }
    }

    suspend fun isAccountNotExists(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            accountsEntityQueries.getAccountById(id).executeAsOneOrNull() == null
        }
    }

    suspend fun deleteAccountById(id: Long) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.deleteAccountById(id)
        }
    }

    fun getAccountsCountFlow(): Flow<Int> {
        return accountsEntityQueries.getAccountsCount()
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toInt() ?: 0 }
    }

    suspend fun updateAccountPosition(position: Int, accountId: Long) {
        withContext(Dispatchers.Default) {
            accountsEntityQueries.updateAccountPositionById(position = position, id = accountId)
        }
    }

    suspend fun addDefaultAccount(context: Context, currency: Currency) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.insertAccount(
                id = 1,
                type = Account.Type.Cash,
                name = MR.strings.account_type_cash.localizedString(context),
                balance = 0.0,
                colorId = AccountColorModel.EastBay.id,
                currency = currency.code,
                position = 0
            )
        }
    }
}