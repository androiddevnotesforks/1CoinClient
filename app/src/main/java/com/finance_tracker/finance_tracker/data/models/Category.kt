package com.finance_tracker.finance_tracker.data.models

import androidx.annotation.DrawableRes

data class Category(
    val name: String,
    @DrawableRes val icon: Int
)