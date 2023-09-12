package com.finance_tracker.finance_tracker.features.add_transaction.views.enter_transaction_controller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.clickableSingle
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Category
import dev.icerock.moko.resources.compose.painterResource
import kotlin.math.roundToInt

@Composable
internal fun CategorySelector(
    withAddButton: Boolean,
    categories: List<Category>,
    onCategorySelect: (category: Category) -> Unit,
    onCategoryAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    var gridWidth by remember { mutableStateOf(1) }
    var cellWidth by remember { mutableStateOf(1) }
    val columnCount by remember {
        derivedStateOf {
            (gridWidth.toFloat() / cellWidth)
                .roundToInt()
                .coerceAtLeast(1)
        }
    }

    val dividerWidth = 0.5.dp
    val dividerColor = CoinTheme.color.dividers

    val cellHeight = 68.dp
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxHeight()
            .onSizeChanged {
                gridWidth = it.width
            },
        columns = GridCells.Adaptive(120.dp)
    ) {
        itemsIndexed(categories) { index, category ->
            BoxWithDividers(
                modifier = Modifier
                    .onSizeChanged {
                        if (index == 0) {
                            cellWidth = it.width
                        }
                    }
                    .clickableSingle {
                        onCategorySelect(category)
                    },
                columnCount = columnCount,
                index = index,
                dividerWidth = dividerWidth,
                dividerColor = dividerColor
            ) {
                CategoryCard(
                    modifier = Modifier
                        .height(cellHeight)
                        .fillMaxSize(),
                    category = category
                )
            }
        }
        if (withAddButton) {
            item {
                AddCell(
                    columnCount = columnCount,
                    index = categories.size,
                    dividerWidth = dividerWidth,
                    dividerColor = dividerColor,
                    cellHeight = cellHeight,
                    onCellWidthChange = { cellWidth = it },
                    onClick = onCategoryAdd
                )
            }
        }
    }
}

@Composable
private fun CategoryCard(
    category: Category,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(category.icon),
            contentDescription = null
        )
        Text(
            text = category.name,
            style = CoinTheme.typography.body1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}