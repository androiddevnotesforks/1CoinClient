package com.finance_tracker.finance_tracker.domain.models

import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.toString
import dev.icerock.moko.resources.StringResource

data class MonthExpenseLimitChartData(
    val pieces: List<Piece>,
    val totalLimit: Amount,
    val spent: Amount
) {
    data class Piece(
        val type: MonthExpenseLimitType,
        val showLabel: Boolean,
        val amount: Amount,
        val percent: Float,
        val percentInLabel: Float = percent
    ) {
        val percentage = percentInLabel.toString(PERCENT_PRECISION)

        companion object {
            const val PERCENT_PRECISION = 0
        }
    }

    enum class MonthExpenseLimitType(
        val itemName: StringResource
    ) {
        PlannedSpent(
            itemName = MR.strings.month_budget_widget_money_spent
        ),
        OverSpent(
            itemName = MR.strings.month_budget_widget_exceeding_limit
        ),
        Remain(
            itemName = MR.strings.month_budget_widget_remain_money
        )
    }

    companion object {
        val Empty = MonthExpenseLimitChartData(
            pieces = emptyList(),
            totalLimit = Amount.default,
            spent = Amount.default
        )
    }
}