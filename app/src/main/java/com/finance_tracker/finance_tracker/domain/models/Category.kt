package com.finance_tracker.finance_tracker.domain.models

import androidx.annotation.DrawableRes
import com.finance_tracker.finance_tracker.R

data class Category(
    val id: Long,
    val name: String,
    @DrawableRes val icon: Int
) {
    companion object {
        val EMPTY = Category(
            id = -1,
            name = "Correct",
            icon = R.drawable.ic_category_13
        )
    }
}