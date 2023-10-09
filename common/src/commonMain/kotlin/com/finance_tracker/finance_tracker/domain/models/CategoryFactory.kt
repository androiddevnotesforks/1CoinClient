package com.finance_tracker.finance_tracker.domain.models

import dev.icerock.moko.resources.desc.ResourceStringDesc
import dev.icerock.moko.resources.desc.image.ImageDescResource

expect fun IncomeCategory(
    id: Long,
    name: ResourceStringDesc,
    icon: ImageDescResource
): Category

expect fun ExpenseCategory(
    id: Long,
    name: ResourceStringDesc,
    icon: ImageDescResource
): Category

expect fun Category.Companion.empty(): Category