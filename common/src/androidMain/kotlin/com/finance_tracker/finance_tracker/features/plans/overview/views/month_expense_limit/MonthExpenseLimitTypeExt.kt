package com.finance_tracker.finance_tracker.features.plans.overview.views.month_expense_limit

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.MonthExpenseLimitChartData

@Composable
internal fun MonthExpenseLimitChartData.MonthExpenseLimitType.backgroundColor(): Color {
    return when (this) {
        MonthExpenseLimitChartData.MonthExpenseLimitType.PlannedSpent -> {
            CoinTheme.color.primary
        }
        MonthExpenseLimitChartData.MonthExpenseLimitType.Remain -> {
            CoinTheme.color.dividers
        }
        MonthExpenseLimitChartData.MonthExpenseLimitType.OverSpent -> {
            CoinTheme.color.accentRed
        }
    }
}

@Composable
internal fun MonthExpenseLimitChartData.MonthExpenseLimitType.contentColor(): Color {
    return when (this) {
        MonthExpenseLimitChartData.MonthExpenseLimitType.PlannedSpent -> {
            CoinTheme.color.backgroundSurface
        }
        MonthExpenseLimitChartData.MonthExpenseLimitType.Remain -> {
            CoinTheme.color.secondary
        }
        MonthExpenseLimitChartData.MonthExpenseLimitType.OverSpent -> {
            CoinTheme.color.backgroundSurface
        }
    }
}