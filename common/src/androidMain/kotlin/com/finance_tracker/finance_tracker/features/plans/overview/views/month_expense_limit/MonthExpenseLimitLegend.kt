package com.finance_tracker.finance_tracker.features.plans.overview.views.month_expense_limit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.MonthExpenseLimitChartData
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun MonthExpenseLimitLegend(
    types: List<MonthExpenseLimitChartData.MonthExpenseLimitType>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        types.forEach { type ->
            LegendItem(type)
        }
    }
}

@Composable
private fun LegendItem(
    type: MonthExpenseLimitChartData.MonthExpenseLimitType,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(type.backgroundColor())
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = stringResource(type.itemName),
            style = CoinTheme.typography.subtitle2
        )
    }
}
