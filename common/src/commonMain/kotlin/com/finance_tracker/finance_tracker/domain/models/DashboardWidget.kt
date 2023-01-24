package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.StringResource

data class DashboardWidget(
    val id: Int,
    val name: StringResource,
    val isRequired: Boolean,
    val isEnabled: Boolean
) {
    companion object {
        val all = listOf(
            DashboardWidget(
                id = 0,
                name = MR.strings.tab_income,
                isRequired = true,
                isEnabled = true
            ),
            DashboardWidget(
                id = 1,
                name = MR.strings.tab_income,
                isRequired = true,
                isEnabled = true
            ),
            DashboardWidget(
                id = 2,
                name = MR.strings.tab_income,
                isRequired = true,
                isEnabled = true
            )
        )
    }
}
