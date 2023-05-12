package com.finance_tracker.finance_tracker.features.plans.views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Budget



@Suppress("MagicNumber")
@Composable
internal fun BudgetByCategoriesWidget(
    modifier: Modifier = Modifier,
    budgets: List<Budget> = emptyList(),
) {
    Column(
        modifier = modifier
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
            )
    ) {
        BudgetsByCategoriesHeader()

        if (budgets.isEmpty()) {
            BudgetsEmptyStub(
                modifier = Modifier
                    .padding(top = 12.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = CoinTheme.color.dividers,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .`if`(budgets.size <= 5) {
                        padding(16.dp)
                    }
                    .`if`(budgets.size > 5) {
                        padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    }
                    .animateContentSize()
            ) {
                BudgetsView(budgets = budgets)
            }
        }
    }
}