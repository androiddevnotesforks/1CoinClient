package com.finance_tracker.finance_tracker.data.database.mappers

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.domain.models.Category
import com.financetracker.financetracker.data.CategoriesEntity
import dev.icerock.moko.resources.getImageByFileName

fun CategoriesEntity.categoryToDomainModel(): Category {
    return Category(
        id = id,
        name = name,
        icon = MR.images.getImageByFileName(icon) ?: error("No icon by name $icon"),
        isExpense = isExpense,
        isIncome = isIncome
    )
}