package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.ActivityContextStorage
import com.finance_tracker.finance_tracker.core.common.asImageDescResource
import dev.icerock.moko.resources.desc.ResourceStringDesc
import dev.icerock.moko.resources.desc.image.ImageDescResource

actual fun IncomeCategory(
    id: Long,
    name: ResourceStringDesc,
    icon: ImageDescResource,
): Category {
    return Category(
        id = id,
        name = name.toString(ActivityContextStorage.getContext()),
        icon = icon,
        isIncome = true,
        isExpense = false
    )
}

actual fun ExpenseCategory(
    id: Long,
    name: ResourceStringDesc,
    icon: ImageDescResource,
): Category {
    return Category(
        id = id,
        name = name.toString(ActivityContextStorage.getContext()),
        icon = icon,
        isIncome = false,
        isExpense = true
    )
}

actual fun Category.Companion.empty(): Category {
    return Category(
        id = -1,
        name = MR.strings.category_uncategorized.getString(ActivityContextStorage.getContext()),
        icon = MR.images.ic_category_uncategorized.asImageDescResource(),
        isExpense = true,
        isIncome = true
    )
}