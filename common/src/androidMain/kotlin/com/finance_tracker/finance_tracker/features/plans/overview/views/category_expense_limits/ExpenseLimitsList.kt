package com.finance_tracker.finance_tracker.features.plans.overview.views.category_expense_limits

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.ExpenseLimitItem
import com.finance_tracker.finance_tracker.domain.models.Plan
import dev.icerock.moko.resources.compose.painterResource

private const val MaxBudgetsBeforeUncoverWidget = 5
private const val ArrowRotationDurationMillis = 250

@Suppress("MagicNumber")
@Composable
internal fun ExpenseLimitsList(
    onLimitClick: (Plan) -> Unit,
    modifier: Modifier = Modifier,
    plans: List<Plan> = emptyList()
) {
    Column(
        modifier = modifier
            .padding(vertical = 10.dp)
            .animateContentSize()
    ) {
        var expanded by remember { mutableStateOf(false) }
        val filteredPlans = if (expanded) {
            plans
        } else {
            plans.take(MaxBudgetsBeforeUncoverWidget)
        }

        filteredPlans.forEach { plan ->
            key(plan.category.id) {
                ExpenseLimitItem(
                    plan = plan,
                    onClick = { onLimitClick(plan) }
                )
            }
        }
        if (plans.size > MaxBudgetsBeforeUncoverWidget) {
            var isRotated by remember { mutableStateOf(false) }

            val rotationAngle by animateFloatAsState(
                targetValue = if (isRotated) 180f else 0f,
                animationSpec = tween(
                    durationMillis = ArrowRotationDurationMillis,
                    easing = FastOutLinearInEasing
                ),
                label = "FloatAnimation"
            )
            Icon(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp,
                    )
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable {
                        expanded = !expanded
                        isRotated = !isRotated
                    }
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally)
                    .rotate(rotationAngle),
                painter = painterResource(MR.images.ic_arrow_down_plans),
                contentDescription = null,
                tint = CoinTheme.color.primary
            )
        }
    }
}