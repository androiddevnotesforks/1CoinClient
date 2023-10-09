package com.finance_tracker.finance_tracker.domain.models

import dev.icerock.moko.resources.desc.image.ImageDescResource

data class Category(
    val id: Long,
    val name: String,
    val icon: ImageDescResource,
    val isExpense: Boolean,
    val isIncome: Boolean
) {
    companion object;
}