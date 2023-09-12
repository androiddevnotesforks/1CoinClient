package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.localizedString
import dev.icerock.moko.resources.ImageResource

data class Category(
    val id: Long,
    val name: String,
    val icon: ImageResource,
    val isExpense: Boolean,
    val isIncome: Boolean
) {
    companion object {

        fun empty(context: Context): Category {
            return Category(
                id = -1,
                name = MR.strings.category_uncategorized.localizedString(context),
                icon = MR.images.ic_category_uncategorized,
                isExpense = true,
                isIncome = true
            )
        }
    }
}

fun IncomeCategory(
    id: Long,
    name: String,
    icon: ImageResource,
): Category {
    return Category(
        id = id,
        name = name,
        icon = icon,
        isIncome = true,
        isExpense = false
    )
}

fun ExpenseCategory(
    id: Long,
    name: String,
    icon: ImageResource,
): Category {
    return Category(
        id = id,
        name = name,
        icon = icon,
        isIncome = false,
        isExpense = true
    )
}