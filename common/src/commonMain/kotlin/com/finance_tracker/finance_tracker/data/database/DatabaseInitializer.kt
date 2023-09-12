package com.finance_tracker.finance_tracker.data.database

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.getRaw
import com.finance_tracker.finance_tracker.core.common.localizedString
import com.finance_tracker.finance_tracker.core.common.toCategoryString
import com.finance_tracker.finance_tracker.data.settings.AccountSettings
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.ExpenseCategory
import com.finance_tracker.finance_tracker.domain.models.IncomeCategory
import com.financetracker.financetracker.data.AccountsEntityQueries
import com.financetracker.financetracker.data.CategoriesEntityQueries
import com.financetracker.financetracker.data.CurrencyRatesEntityQueries
import com.financetracker.financetracker.data.DbVersionEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive

class DatabaseInitializer(
    private val jsonFactory: Json,
    private val accountSettings: AccountSettings,
    private val categoriesEntityQueries: CategoriesEntityQueries,
    private val accountsEntityQueries: AccountsEntityQueries,
    private val currencyRatesEntityQueries: CurrencyRatesEntityQueries,
    private val dbVersionEntityQueries: DbVersionEntityQueries
) {
    suspend fun init(context: Context) {
        withContext(Dispatchers.IO) {
            initDbVersion()

            if (!accountSettings.isInitDefaultData()) {
                initCategories(context)
                initCurrencyRates(context)
                accountSettings.setIsInitDefaultData(true)
            }

            if (!accountSettings.isInitAccountPositions()) {
                initAccountPositions()
                accountSettings.setIsInitAccountPositions(true)
            }
        }
    }

    private fun initDbVersion() {
        dbVersionEntityQueries.setVersion(CurrentDbVersion)
    }

    private fun initCategories(context: Context) {
        categoriesEntityQueries.transaction {
            val databaseCategories = categoriesEntityQueries.getAllCategories().executeAsList()

            if (databaseCategories.isEmpty()) {
                createDefaultExpenseCategories(context).forEach { category ->
                    categoriesEntityQueries.insertCategory(
                        id = category.id,
                        name = category.name,
                        icon = category.icon.toCategoryString(),
                        position = category.id,
                        isExpense = true,
                        isIncome = false,
                    )
                }
                createDefaultIncomeCategories(context).forEach { category ->
                    categoriesEntityQueries.insertCategory(
                        id = category.id,
                        name = category.name,
                        icon = category.icon.toCategoryString(),
                        position = category.id,
                        isExpense = false,
                        isIncome = true,
                    )
                }
            }
        }
    }

    private fun createDefaultExpenseCategories(context: Context): List<Category> {
        return listOf(
            ExpenseCategory(
                id = 0,
                name = MR.strings.category_restoraunt.localizedString(context),
                icon = MR.images.ic_category_1
            ),
            ExpenseCategory(
                id = 1,
                name = MR.strings.category_health.localizedString(context),
                icon = MR.images.ic_category_2
            ),
            ExpenseCategory(
                id = 2,
                name = MR.strings.category_child.localizedString(context),
                icon = MR.images.ic_category_3
            ),
            ExpenseCategory(
                id = 3,
                name = MR.strings.category_car.localizedString(context),
                icon = MR.images.ic_category_4
            ),
            ExpenseCategory(
                id = 4,
                name = MR.strings.category_education.localizedString(context),
                icon = MR.images.ic_category_5
            ),
            ExpenseCategory(
                id = 5,
                name = MR.strings.category_entertainment.localizedString(context),
                icon = MR.images.ic_category_6
            ),
            ExpenseCategory(
                id = 6,
                name = MR.strings.category_sport.localizedString(context),
                icon = MR.images.ic_category_7
            ),
            ExpenseCategory(
                id = 7,
                name = MR.strings.category_public_transport.localizedString(context),
                icon = MR.images.ic_category_8
            ),
            ExpenseCategory(
                id = 8,
                name = MR.strings.category_shop.localizedString(context),
                icon = MR.images.ic_category_9
            ),
            ExpenseCategory(
                id = 9,
                name = MR.strings.category_utilities.localizedString(context),
                icon = MR.images.ic_category_10
            ),
            ExpenseCategory(
                id = 10,
                name = MR.strings.category_clothes.localizedString(context),
                icon = MR.images.ic_category_11
            ),
            ExpenseCategory(
                id = 11,
                name = MR.strings.category_electronics.localizedString(context),
                icon = MR.images.ic_category_12
            ),
            ExpenseCategory(
                id = 12,
                name = MR.strings.category_correct.localizedString(context),
                icon = MR.images.ic_category_13
            )
        )
    }

    private fun createDefaultIncomeCategories(context: Context): List<Category> {
        return listOf(
            IncomeCategory(
                id = 13,
                name = MR.strings.category_salary.localizedString(context),
                icon = MR.images.ic_category_14
            ),
            IncomeCategory(
                id = 14,
                name = MR.strings.category_allowance.localizedString(context),
                icon = MR.images.ic_category_15
            ),
            IncomeCategory(
                id = 15,
                name = MR.strings.category_gift.localizedString(context),
                icon = MR.images.ic_category_16
            )
        )
    }

    private fun initCurrencyRates(context: Context) {
        val currencyRatesJson = getRaw(context, "base_rates", "json")
        currencyRatesEntityQueries.transaction {
            val databaseCurrencyRates = currencyRatesEntityQueries.getAllRates().executeAsList()

            if (databaseCurrencyRates.isEmpty()) {
                val currencyRatesObject = jsonFactory.decodeFromString<JsonObject>(currencyRatesJson)
                currencyRatesObject.forEach { (key, value) ->
                    currencyRatesEntityQueries.insertNewRate(
                        currency = key,
                        rate = value.jsonPrimitive.double
                    )
                }
            }
        }
    }

    private fun initAccountPositions() {
        val allAccounts = accountsEntityQueries.getAllAccounts().executeAsList()

        allAccounts.forEachIndexed { index, accountsEntity ->
            accountsEntityQueries.updateAccountPositionById(index, accountsEntity.id)
        }
    }

    companion object {
        private const val CurrentDbVersion = 9L
    }
}