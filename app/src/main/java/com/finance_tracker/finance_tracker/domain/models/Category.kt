package com.finance_tracker.finance_tracker.domain.models

import androidx.annotation.DrawableRes

data class Category(
    val name: String,
    @DrawableRes val icon: Int
)