package com.finance_tracker.finance_tracker.data.database.mappers

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.getCategoryIconByNameOrDefault
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Plan

internal val EmptyCategoryId = -1L

val fullPlanMapper: (

    // Plan
    id: Long,
    categoryId: Long?,
    limitAmount: Double,
    currencyCode: String,
    year: Long,
    month: Long,

    // Category
    id_: Long?,
    name: String?,
    icon: String?,
    position: Long?,
    isExpense: Boolean?,
    isIncome: Boolean?
) -> Plan = {
    // Plan
    id, planCategoryId, limitAmount, currencyCode, _, _,
    // Category
    categoryId, categoryName, categoryIcon, _, isExpense, isIncome ->

    val currency = Currency.getByCode(currencyCode)
    Plan(
        id = id,
        category = Category(
            id = categoryId ?: EmptyCategoryId,
            name = categoryName.orEmpty(),
            icon = MR.images.getCategoryIconByNameOrDefault(categoryIcon),
            isExpense = isExpense ?: false,
            isIncome = isIncome ?: false
        ),
        spentAmount = Amount(
            amountValue = 0.0,
            currency = currency
        ),
        limitAmount = Amount(
            amountValue = limitAmount,
            currency = currency
        )
    )
}