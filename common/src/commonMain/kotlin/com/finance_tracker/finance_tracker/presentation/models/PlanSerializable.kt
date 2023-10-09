package com.finance_tracker.finance_tracker.presentation.models

import com.finance_tracker.finance_tracker.domain.models.Plan
import kotlinx.serialization.Serializable

@Serializable
data class PlanSerializable(
    val id: Long? = null,
    val category: CategorySerializable,
    val spentAmount: AmountSerializable,
    val limitAmount: AmountSerializable
)

fun Plan.toSerializable(): PlanSerializable {
    return PlanSerializable(
        id = id,
        category = category.toSerializable(),
        spentAmount = spentAmount.toSerializable(),
        limitAmount = limitAmount.toSerializable()
    )
}

fun PlanSerializable.toDomain(): Plan {
    return Plan(
        id = id,
        category = category.toDomain(),
        spentAmount = spentAmount.toDomain(),
        limitAmount = limitAmount.toDomain()
    )
}