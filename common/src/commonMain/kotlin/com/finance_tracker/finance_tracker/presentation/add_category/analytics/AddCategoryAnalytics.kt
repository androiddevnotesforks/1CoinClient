package com.finance_tracker.finance_tracker.presentation.add_category.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab

class AddCategoryAnalytics: BaseAnalytics() {

    override val screenName = "AddCategoryScreen"

    fun onBackClick() {
        trackClick(eventName = "Back")
    }

    fun onAddCategoryClick(
        transactionTypeTab: TransactionTypeTab,
        iconName: String,
        categoryName: String
    ) {
        trackClick(
            eventName = "Add",
            properties = mapOf(
                "type" to transactionTypeTab.analyticsName,
                "icon_name" to iconName,
                "category_name" to categoryName
            )
        )
    }
}