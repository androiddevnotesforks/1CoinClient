package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Plan
import dev.icerock.moko.resources.compose.painterResource

@Composable
internal fun ExpenseLimitItem(
    plan: Plan,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 6.dp, horizontal = 16.dp)
    ) {
        Icon(
            painter = painterResource(plan.category.icon),
            contentDescription = null,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(
                    color = CoinTheme.color.secondaryBackground,
                    shape = CircleShape
                )
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            tint = CoinTheme.color.content
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .padding(end = 12.dp),
                    text = plan.category.name,
                    style = CoinTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = CoinTheme.color.content
                )
                Spacer(modifier = Modifier.weight(1f))
                SpentAndLimitAmounts(
                    spentAmount = plan.spentAmount,
                    limitAmount = plan.limitAmount
                )
            }
            val progress by remember(plan.spentAmount, plan.limitAmount) {
                derivedStateOf {
                    (plan.spentAmount.amountValue / plan.limitAmount.amountValue).toFloat()
                }
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(8.dp),
                color = if (progress > 1) {
                    CoinTheme.color.accentRed
                } else {
                    CoinTheme.color.primary
                },
                backgroundColor = if (progress > 1) {
                    CoinTheme.color.primary
                } else {
                    CoinTheme.color.secondaryBackground
                },
                strokeCap = StrokeCap.Round,
                progress = when {
                    plan.limitAmount.amountValue == 0.0 -> {
                        1f
                    }
                    plan.spentAmount.amountValue <= plan.limitAmount.amountValue -> {
                        (plan.spentAmount.amountValue / plan.limitAmount.amountValue).toFloat()
                    }
                    else -> {
                        (plan.spentAmount.amountValue / plan.limitAmount.amountValue - 1f).toFloat()
                    }
                }
            )
        }
    }
}

@Composable
private fun SpentAndLimitAmounts(
    spentAmount: Amount,
    limitAmount: Amount,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.widthIn(max = 80.dp),
            text = "${spentAmount.currency.symbol}${spentAmount.amountValue.format()}",
            style = CoinTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = CoinTheme.color.content,
        )
        Text(
            text = "/",
            style = CoinTheme.typography.body1,
            color = CoinTheme.color.content
        )
        Text(
            modifier = Modifier.widthIn(max = 80.dp),
            text = "${limitAmount.currency.symbol}${limitAmount.amountValue.format()}",
            style = CoinTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = CoinTheme.color.content
        )
    }
}