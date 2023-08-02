package com.finance_tracker.finance_tracker.features.plans.overview.views.month_expense_limit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.MonthExpenseLimitChartData
import com.finance_tracker.finance_tracker.features.analytics.views.CoinBezierLabelConnector
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi

private val PieChartSize = 240.dp
private const val MaxPercentValue = ">999"
private const val MaxPercentLength = 3

@Suppress("MagicNumber")
@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
internal fun MonthExpenseLimitPieChart(
    monthExpenseLimitChartData: MonthExpenseLimitChartData,
    modifier: Modifier = Modifier
) {
    ChartLayout(
        modifier = modifier
            .padding(16.dp)
    ) {
        PieChart(
            modifier = Modifier
                .fillMaxWidth(),
            values = monthExpenseLimitChartData.pieces.map { it.percent },
            minPieDiameter = PieChartSize,
            maxPieDiameter = PieChartSize,
            slice = { index: Int ->
                val piece = monthExpenseLimitChartData.pieces.getOrNull(index)
                if (piece != null) {
                    DefaultSlice(
                        color = piece.type.backgroundColor(),
                        hoverExpandFactor = 1.05f,
                        hoverElement = { Text(piece.amount.toString()) }
                    )
                }
            },
            label = { index ->
                val piece = monthExpenseLimitChartData.pieces.getOrNull(index)
                if (piece != null && piece.showLabel) {
                    val percentage = if (piece.percentage.length > MaxPercentLength) {
                        MaxPercentValue
                    } else {
                        piece.percentage
                    }
                    Text(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(piece.type.backgroundColor())
                            .padding(horizontal = 6.dp, vertical = 3.dp),
                        text = "$percentage%",
                        style = CoinTheme.typography.subtitle4,
                        color = piece.type.contentColor(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            labelConnector = { index ->
                val piece = monthExpenseLimitChartData.pieces.getOrNull(index)
                if (piece != null && piece.showLabel) {
                    CoinBezierLabelConnector(
                        connectorColor = piece.type.backgroundColor()
                    )
                }
            },
            holeSize = 0.8f,
            holeContent = {
                HoleTotalLabel(
                    data = if (monthExpenseLimitChartData == MonthExpenseLimitChartData.Empty) {
                        HoleTotalLabelData.Loading
                    } else {
                        HoleTotalLabelData.Content(
                            spentAmount = monthExpenseLimitChartData.spent,
                            limitAmount = monthExpenseLimitChartData.totalLimit
                        )
                    }
                )
            },
            labelSpacing = 1.05f
        )
    }
}