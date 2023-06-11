package com.finance_tracker.finance_tracker.features.plans.overview.views.month_expense_limit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.ui.CoinWidget
import com.finance_tracker.finance_tracker.domain.models.MonthExpenseLimitChartData
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun MonthExpenseLimitWidget(
    monthExpenseLimitChartData: MonthExpenseLimitChartData,
    modifier: Modifier = Modifier
) {
    CoinWidget(
        modifier = modifier,
        title = stringResource(MR.strings.month_budget_widget_title),
        withBorder = true
    ) {
        MonthExpenseLimitWidgetContent(
            monthExpenseLimitChartData = monthExpenseLimitChartData
        )
    }
}

@Composable
private fun MonthExpenseLimitWidgetContent(
    monthExpenseLimitChartData: MonthExpenseLimitChartData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(bottom = 24.dp)
    ) {
        MonthExpenseLimitPieChart(monthExpenseLimitChartData)
        MonthExpenseLimitLegend(
            types = monthExpenseLimitChartData.pieces.mapNotNull { it.type }
        )
    }
}