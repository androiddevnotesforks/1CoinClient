package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.DecimalFormatType
import com.finance_tracker.finance_tracker.core.common.date.localizedName
import com.finance_tracker.finance_tracker.core.common.toPx
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.core.ui.MonthsList
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.pie.BezierLabelConnector
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.pie.StraightLineConnector
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.util.toString
import kotlinx.datetime.Month

private const val MinPercentPrecision = 1

private data class OtherOptionsState(
    val showLabels: Boolean = true,
    val holeSize: Float = 0.8f,
    val labelSpacing: Float = 1.1f
)

private data class ConnectorStyleState(
    val strokeStyle: Stroke,
    val straightLine: Boolean
)

@Suppress("MagicNumber")
@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun CategoriesPieChart(
    monthTransactionsByCategory: TxsByCategoryChart?,
    selectedMonth: Month,
    modifier: Modifier = Modifier,
    onMonthSelect: (Month) -> Unit = {}
) {
    val totalAmount = monthTransactionsByCategory?.total ?: 0.0
    val txsByCategoryChartPieces = monthTransactionsByCategory?.pieces.orEmpty()

    val strokeWidth = 1.dp.toPx()
    val otherOptionsState by remember { mutableStateOf(OtherOptionsState()) }
    val connectorStyle by remember {
        mutableStateOf(
            ConnectorStyleState(
                strokeStyle = Stroke(
                    width = strokeWidth
                ),
                straightLine = false
            )
        )
    }

    Column(
        modifier = modifier
    ) {

        MonthsList(
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 24.dp
            ),
            selectedMonth = selectedMonth,
            onMonthSelect = onMonthSelect
        )

        if (txsByCategoryChartPieces.isNotEmpty()) {
            ChartLayout(
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp,
                )
            ) {
                PieChart(
                    values = txsByCategoryChartPieces.map { it.amount.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    slice = { i: Int ->
                        DefaultSlice(
                            color = txsByCategoryChartPieces[i].color,
                            hoverExpandFactor = 1.05f,
                            hoverElement = { Text(txsByCategoryChartPieces[i].amount.toString()) }
                        )
                    },
                    label = { i ->
                        if (otherOptionsState.showLabels) {
                            Text(
                                text = (txsByCategoryChartPieces[i].amount.toFloat() / totalAmount.toFloat())
                                    .toPercent(MinPercentPrecision)
                            )
                        }
                    },
                    labelConnector = { i ->
                        if (otherOptionsState.showLabels) {
                            if (connectorStyle.straightLine) {
                                StraightLineConnector(
                                    connectorColor = txsByCategoryChartPieces[i].color,
                                    connectorStroke = connectorStyle.strokeStyle
                                )
                            } else {
                                BezierLabelConnector(
                                    connectorColor = txsByCategoryChartPieces[i].color,
                                    connectorStroke = connectorStyle.strokeStyle
                                )
                            }
                        }
                    },
                    holeSize = otherOptionsState.holeSize,
                    holeContent = {
                        HoleTotalLabel(
                            data = HoleTotalLabelData(
                                month = selectedMonth,
                                currency = Currency.default,
                                amount = totalAmount
                            )
                        )
                    },
                    labelSpacing = if (otherOptionsState.showLabels) otherOptionsState.labelSpacing else 1.0f
                )
            }
        } else {
            Text(
                text = "No transactions" // TODO: Move to resources
            )
        }
    }
}

data class HoleTotalLabelData(
    val month: Month,
    val currency: Currency,
    val amount: Double
)

@Suppress("MagicNumber")
@Composable
private fun HoleTotalLabel(
    data: HoleTotalLabelData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = data.currency.sign + DecimalFormatType.Amount.format(data.amount),
            color = CoinTheme.color.content,
            style = CoinTheme.typography.h2.staticTextSize()
        )
        Text(
            text = data.month.localizedName(),
            color = CoinTheme.color.secondary,
            style = CoinTheme.typography.subtitle2.staticTextSize()
        )
    }
}


private fun Float.toPercent(precision: Int): String {
    @Suppress("MagicNumber")
    return "${(this * 100.0f).toString(precision)}%"
}