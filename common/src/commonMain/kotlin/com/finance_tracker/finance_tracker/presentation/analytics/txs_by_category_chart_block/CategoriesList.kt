package com.finance_tracker.finance_tracker.presentation.analytics.txs_by_category_chart_block

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.TxsByCategoryChart
import com.finance_tracker.finance_tracker.presentation.common.formatters.format
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi

@Composable
fun CategoriesList(
    pieces: List<TxsByCategoryChart.Piece>,
    totalAmount: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        pieces.forEach { piece ->
            CategoryItem(piece, totalAmount)
        }
    }
}

@Composable
fun CategoryItem(
    piece: TxsByCategoryChart.Piece,
    total: Double,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryPieChart(piece, total)

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = piece.category.name,
                style = CoinTheme.typography.body2
            )
            Text(
                text = piece.transactionsCount.toString() + " " + stringResource("analytics_transactions"),
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.secondary
            )
        }

        Text(
            text = piece.amount.format(),
            style = CoinTheme.typography.body2
        )
    }
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
private fun CategoryPieChart(
    piece: TxsByCategoryChart.Piece,
    total: Double,
    modifier: Modifier = Modifier
) {
    val percentage = remember { (piece.amount.amountValue / total * 100).toFloat() }
    val chartSize = 32.dp
    Box(
        modifier = modifier
            .size(chartSize),
        contentAlignment = Alignment.Center
    ) {
        val values = listOf(percentage, 100f - percentage)
        PieChart(
            modifier = Modifier.matchParentSize(),
            minPieDiameter = chartSize,
            maxPieDiameter = chartSize,
            values = values,
            slice = { index: Int ->
                DefaultSlice(
                    color = if (index == 0) {
                        piece.color
                    } else {
                        Color.Transparent
                    }
                )
            },
            holeSize = 0.77f,
            labelConnector = { Spacer(modifier = Modifier.size(0.dp)) },
            animationSpec = tween(durationMillis = 0)
        )

        PieChart(
            modifier = Modifier.matchParentSize(),
            minPieDiameter = chartSize,
            maxPieDiameter = chartSize,
            values = values,
            slice = { index: Int ->
                DefaultSlice(
                    color = if (index == 0) {
                        Color.Transparent
                    } else {
                        CoinTheme.color.dividers
                    }
                )
            },
            holeSize = 0.84f,
            labelConnector = { Spacer(modifier = Modifier.size(0.dp)) },
            animationSpec = tween(durationMillis = 0)
        )

        Text(
            modifier = Modifier
                .`if`(piece.percentage == "100") {
                    padding(top = 1.dp)
                },
            text = piece.percentage,
            style = CoinTheme.typography.subtitle3,
            textAlign = TextAlign.Center
        )
    }
}
