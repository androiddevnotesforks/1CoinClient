package com.finance_tracker.finance_tracker.features.plans.views

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.finance_tracker.finance_tracker.core.common.rememberAsyncImagePainter
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.BudgetCard
import com.finance_tracker.finance_tracker.domain.models.Budget

private const val MaxBudgetsBeforeUncoverWidget = 5

@Suppress("MagicNumber")
@Composable
fun BudgetsView(
    modifier: Modifier = Modifier,
    budgets: List<Budget> = emptyList()
) {

    var isRotated by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 180F else 0F,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutLinearInEasing
        )
    )

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        val filteredBudgets = if (expanded) {
            budgets
        } else {
            budgets.take(MaxBudgetsBeforeUncoverWidget)
        }

        filteredBudgets.forEach { budget ->
            key(budget.category.id) {
                BudgetCard(budget)
            }
        }
        if (budgets.size > MaxBudgetsBeforeUncoverWidget) {
            Icon(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp,
                    )
                    .size(32.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .clickable {
                        expanded = !expanded
                        isRotated = !isRotated
                    }
                    .align(Alignment.CenterHorizontally)
                    .rotate(rotationAngle),
                painter = rememberAsyncImagePainter(MR.files.ic_arrow_down_plans),
                contentDescription = null,
                tint = CoinTheme.color.primary
            )
        }
    }
}