package com.finance_tracker.finance_tracker.domain.models

data class Category(
    val id: Long,
    val name: String,
    val icon: Int
) {
    companion object {
        val EMPTY = Category(
            id = -1,
            name = "Correct",
            icon = -1 // TODO: R.drawable.ic_category_13
        )
    }
}