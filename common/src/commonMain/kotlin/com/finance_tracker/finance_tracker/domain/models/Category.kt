package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.localizedString
import dev.icerock.moko.resources.FileResource

data class Category(
    val id: Long,
    val name: String,
    val icon: FileResource
) {
    companion object {

        fun empty(context: Context): Category {
            return Category(
                id = -1,
                name = MR.strings.category_uncategorized.localizedString(context),
                icon = MR.files.ic_category_uncategorized
            )
        }
    }
}