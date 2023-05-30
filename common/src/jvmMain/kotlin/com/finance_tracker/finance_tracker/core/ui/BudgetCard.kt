package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.common.rememberAsyncImagePainter
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Plan

@Composable
internal fun ExpenseLimitItem(
    plan: Plan,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(
            painter = rememberAsyncImagePainter(plan.category.icon),
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.widthIn(max = 80.dp),
                        text = "${plan.spentAmount.currency.symbol}${plan.spentAmount.amountValue.format()}",
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
                        text = "${plan.limitAmount.currency.symbol}${plan.limitAmount.amountValue.format()}",
                        style = CoinTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = CoinTheme.color.content
                    )
                }
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(8.dp),
                color = CoinTheme.color.primary,
                backgroundColor = CoinTheme.color.secondaryBackground,
                strokeCap = StrokeCap.Round,
                progress = if (plan.limitAmount.amountValue == 0.0) {
                    1f
                } else {
                    (plan.spentAmount.amountValue / plan.limitAmount.amountValue).toFloat()
                }
            )
        }
    }
}