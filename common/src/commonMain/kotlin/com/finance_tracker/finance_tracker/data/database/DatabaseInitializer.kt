package com.finance_tracker.finance_tracker.data.database

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.asImageDescResource
import com.finance_tracker.finance_tracker.core.common.getRaw
import com.finance_tracker.finance_tracker.core.common.toCategoryString
import com.finance_tracker.finance_tracker.data.settings.AccountSettings
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.ExpenseCategory
import com.finance_tracker.finance_tracker.domain.models.IncomeCategory
import com.financetracker.financetracker.data.AccountsEntityQueries
import com.financetracker.financetracker.data.CategoriesEntityQueries
import com.financetracker.financetracker.data.CurrencyRatesEntityQueries
import com.financetracker.financetracker.data.DbVersionEntityQueries
import dev.icerock.moko.resources.desc.desc
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
    suspend fun init() {
        withContext(Dispatchers.IO) {
            initDbVersion()

            if (!accountSettings.isInitDefaultData()) {
                initCategories()
                initCurrencyRates()
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

    private fun initCategories() {
        categoriesEntityQueries.transaction {
            val databaseCategories = categoriesEntityQueries.getAllCategories().executeAsList()

            if (databaseCategories.isEmpty()) {
                createDefaultExpenseCategories().forEach { category ->
                    categoriesEntityQueries.insertCategory(
                        id = category.id,
                        name = category.name,
                        icon = category.icon.toCategoryString(),
                        position = category.id,
                        isExpense = true,
                        isIncome = false,
                    )
                }
                createDefaultIncomeCategories().forEach { category ->
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

    private fun createDefaultExpenseCategories(): List<Category> {
        return listOf(
            ExpenseCategory(
                id = 0,
                name = MR.strings.category_restoraunt.desc(),
                icon = MR.images.ic_category_1.asImageDescResource()
            ),
            ExpenseCategory(
                id = 1,
                name = MR.strings.category_health.desc(),
                icon = MR.images.ic_category_2.asImageDescResource()
            ),
            ExpenseCategory(
                id = 2,
                name = MR.strings.category_child.desc(),
                icon = MR.images.ic_category_3.asImageDescResource()
            ),
            ExpenseCategory(
                id = 3,
                name = MR.strings.category_car.desc(),
                icon = MR.images.ic_category_4.asImageDescResource()
            ),
            ExpenseCategory(
                id = 4,
                name = MR.strings.category_education.desc(),
                icon = MR.images.ic_category_5.asImageDescResource()
            ),
            ExpenseCategory(
                id = 5,
                name = MR.strings.category_entertainment.desc(),
                icon = MR.images.ic_category_6.asImageDescResource()
            ),
            ExpenseCategory(
                id = 6,
                name = MR.strings.category_sport.desc(),
                icon = MR.images.ic_category_7.asImageDescResource()
            ),
            ExpenseCategory(
                id = 7,
                name = MR.strings.category_public_transport.desc(),
                icon = MR.images.ic_category_8.asImageDescResource()
            ),
            ExpenseCategory(
                id = 8,
                name = MR.strings.category_shop.desc(),
                icon = MR.images.ic_category_9.asImageDescResource()
            ),
            ExpenseCategory(
                id = 9,
                name = MR.strings.category_utilities.desc(),
                icon = MR.images.ic_category_10.asImageDescResource()
            ),
            ExpenseCategory(
                id = 10,
                name = MR.strings.category_clothes.desc(),
                icon = MR.images.ic_category_11.asImageDescResource()
            ),
            ExpenseCategory(
                id = 11,
                name = MR.strings.category_electronics.desc(),
                icon = MR.images.ic_category_12.asImageDescResource()
            ),
            ExpenseCategory(
                id = 12,
                name = MR.strings.category_correct.desc(),
                icon = MR.images.ic_category_13.asImageDescResource()
            )
        )
    }

    private fun createDefaultIncomeCategories(): List<Category> {
        return listOf(
            IncomeCategory(
                id = 13,
                name = MR.strings.category_salary.desc(),
                icon = MR.images.ic_category_14.asImageDescResource()
            ),
            IncomeCategory(
                id = 14,
                name = MR.strings.category_allowance.desc(),
                icon = MR.images.ic_category_15.asImageDescResource()
            ),
            IncomeCategory(
                id = 15,
                name = MR.strings.category_gift.desc(),
                icon = MR.images.ic_category_16.asImageDescResource()
            )
        )
    }

    private fun initCurrencyRates() {
        val currencyRatesJson = getRaw(
            name = "base_rates",
            ext = "json"
        )
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