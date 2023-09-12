package com.finance_tracker.finance_tracker.features.plans.overview.views.month_expense_limit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.formatters.ReductionMode
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.domain.models.Amount
import dev.icerock.moko.resources.compose.stringResource

internal sealed class HoleTotalLabelData {

    data class Content(
        val spentAmount: Amount,
        val limitAmount: Amount
    ): HoleTotalLabelData()

    object Loading: HoleTotalLabelData()
}

@Composable
internal fun HoleTotalLabel(
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
        when (data) {
            is HoleTotalLabelData.Content -> {
                Text(
                    text = data.spentAmount.format(
                        reductionMode = ReductionMode.Hard
                    ),
                    color = CoinTheme.color.content,
                    style = CoinTheme.typography.h2.staticTextSize(),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(
                        MR.strings.month_budget_amount_from,
                        data.limitAmount.format(
                            reductionMode = ReductionMode.Hard
                        )
                    ),
                    color = CoinTheme.color.secondary,
                    style = CoinTheme.typography.subtitle4.staticTextSize(),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            HoleTotalLabelData.Loading -> {
                Box(
                    modifier = Modifier
                        .size(width = 100.dp, 36.dp)
                        .padding(bottom = 8.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(CoinTheme.color.secondaryBackground)
                )
            }
        }
    }
}