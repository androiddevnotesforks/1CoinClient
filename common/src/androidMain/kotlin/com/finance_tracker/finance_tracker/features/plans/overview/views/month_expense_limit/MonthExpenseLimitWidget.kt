package com.finance_tracker.finance_tracker.features.plans.overview.views.month_expense_limit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinWidget
import com.finance_tracker.finance_tracker.domain.models.MonthExpenseLimitChartData
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable

@Composable
internal fun MonthExpenseLimitWidget(
    monthExpenseLimitChartData: MonthExpenseLimitChartData,
    onSetLimitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CoinWidget(
        modifier = modifier,
        title = stringResource(MR.strings.month_budget_widget_title),
        withBorder = true,
        action = {
            SetLimitButton(onClick = onSetLimitClick)
        }
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
            types = monthExpenseLimitChartData.pieces.map { it.type }
        )
    }
}

@Composable
private fun SetLimitButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .scaleClickAnimation()
            .noRippleClickable { onClick() }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(MR.images.ic_edit),
            contentDescription = null,
            tint = CoinTheme.color.primary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(MR.strings.plans_btn_set_limit),
            style = CoinTheme.typography.subtitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = CoinTheme.color.primary
        )
    }
}