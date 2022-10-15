package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.core.common.hexToColor
import com.finance_tracker.finance_tracker.presentation.add_account.dropdown_menus.AccountColorData
import com.financetracker.financetracker.AccountColorsEntityQueries
import com.financetracker.financetracker.AccountsEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class AccountsRepository(
    private val accountsEntityQueries: AccountsEntityQueries,
    private val accountColorsEntityQueries: AccountColorsEntityQueries
) {

    suspend fun insertAccount(
        accountName: String,
        balance: Double,
        colorHex: String
    ) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.insertAccount(
                id = null,
                name = accountName,
                balance = balance,
                colorHex = colorHex
            )
        }
    }

    suspend fun getAllAccountColors(): List<AccountColorData> {
        return withContext(Dispatchers.IO) {
            accountColorsEntityQueries.getAllAccountColors { hex, name ->
                AccountColorData(
                    color = hex.hexToColor(),
                    name = name
                )
            }.executeAsList()
        }
    }
}