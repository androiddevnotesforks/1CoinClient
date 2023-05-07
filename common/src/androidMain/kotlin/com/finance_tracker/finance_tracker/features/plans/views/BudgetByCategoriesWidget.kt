package com.finance_tracker.finance_tracker.features.plans.views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.rememberAsyncImagePainter
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.BudgetCard
import com.finance_tracker.finance_tracker.domain.models.Budget
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun BudgetByCategoriesWidget(
    modifier: Modifier = Modifier,
    budgets: List<Budget> = emptyList(),
) {

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
            )
    ) {
        Row {
            Text(
                text = stringResource(MR.strings.budgets_by_categories),
                style = CoinTheme.typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = CoinTheme.color.content
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = CoinTheme.color.dividers,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                if (budgets.size <= 5) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(budgets) { budget ->
                            BudgetCard(budget = budget)
                        }
                    }
                } else if (budgets.size > 5 && !expanded) {
                    LazyColumn(
                        modifier = Modifier.animateContentSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(budgets.subList(0,5)) { budget ->
                            BudgetCard(budget = budget)
                        }
                    }
                } else if (budgets.size > 5 && expanded) {
                    LazyColumn(
                        modifier = Modifier.animateContentSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(budgets) { budget ->
                            BudgetCard(budget = budget)
                        }
                    }
                } else if (budgets.isEmpty()) {
                    BudgetsEmptyStub()
                }
                Icon(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable {
                            expanded = !expanded
                        }
                        .align(Alignment.CenterHorizontally),
                    painter = rememberAsyncImagePainter(MR.files.ic_arrow_down),
                    contentDescription = null,
                    tint = CoinTheme.color.primary
                )
            }
        }
    }
}