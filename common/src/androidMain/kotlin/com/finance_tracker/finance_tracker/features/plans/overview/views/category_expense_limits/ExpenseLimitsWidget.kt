package com.finance_tracker.finance_tracker.features.plans.overview.views.category_expense_limits

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
import com.finance_tracker.finance_tracker.domain.models.Plan
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable

@Suppress("MagicNumber")
@Composable
internal fun ExpenseLimitsWidget(
    onAddLimitClick: () -> Unit,
    onLimitClick: (Plan) -> Unit,
    modifier: Modifier = Modifier,
    plans: List<Plan> = emptyList()
) {
    CoinWidget(
        modifier = modifier,
        title = stringResource(MR.strings.plans_expense_limits),
        withBorder = true,
        action = {
            AddLimitButton(onClick = onAddLimitClick)
        }
    ) {
        ExpenseLimitsWidgetContent(
            modifier = modifier,
            plans = plans,
            onLimitClick = onLimitClick
        )
    }
}

@Composable
private fun ExpenseLimitsWidgetContent(
    onLimitClick: (Plan) -> Unit,
    modifier: Modifier = Modifier,
    plans: List<Plan> = emptyList()
) {
    if (plans.isEmpty()) {
        ExpenseLimitsEmptyStub(
            modifier = modifier
        )
    } else {
        ExpenseLimitsList(
            modifier = modifier,
            plans = plans,
            onLimitClick = onLimitClick
        )
    }
}

@Composable
private fun AddLimitButton(
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
            modifier = Modifier.size(24.dp),
            painter = painterResource(MR.images.ic_plus),
            contentDescription = null,
            tint = CoinTheme.color.primary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(MR.strings.plans_btn_add_limit),
            style = CoinTheme.typography.subtitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = CoinTheme.color.primary
        )
    }
}
