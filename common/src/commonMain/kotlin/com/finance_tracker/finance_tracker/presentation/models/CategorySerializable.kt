package com.finance_tracker.finance_tracker.presentation.models

import com.finance_tracker.finance_tracker.domain.models.Category
import dev.icerock.moko.resources.desc.image.ImageDesc
import dev.icerock.moko.resources.desc.image.ImageDescResource
import kotlinx.serialization.Serializable

@Serializable
data class CategorySerializable(
    val id: Long,
    val name: String,
    val icon: ImageDesc,
    val isExpense: Boolean,
    val isIncome: Boolean
)

fun Category.toSerializable(): CategorySerializable {
    return CategorySerializable(
        id = id,
        name = name,
        icon = icon,
        isExpense = isExpense,
        isIncome = isIncome
    )
}

fun CategorySerializable.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        icon = icon as ImageDescResource,
        isExpense = isExpense,
        isIncome = isIncome
    )
}