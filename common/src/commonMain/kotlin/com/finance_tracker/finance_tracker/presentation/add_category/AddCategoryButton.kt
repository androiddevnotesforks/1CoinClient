package com.finance_tracker.finance_tracker.presentation.add_category

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.getViewModel
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinDropdownMenu
import com.finance_tracker.finance_tracker.core.ui.DropdownMenuItem
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Category

@Composable
fun AddCategoryButton(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    viewModel: AddCategoryViewModel = getViewModel()
) {

    var expanded by remember { mutableStateOf(false) }

    val chosenCategory by viewModel.chosenCategory.collectAsState()

    Box {
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .border(
                    width = 1.dp,
                    color = CoinTheme.color.content.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp)
                ),
            onClick = { expanded = true },
        ) {
            Icon(
                painter = rememberVectorPainter(chosenCategory.iconId),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .align(Alignment.Center),
            )
        }

        Box {
            CoinDropdownMenu(
                modifier = Modifier
                    .width(200.dp)
                    .requiredSizeIn(maxHeight = 180.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                xOffset = 48.dp,
                yOffset = 48.dp,
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            viewModel.setChosenCategory(category)
                        },
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = rememberVectorPainter(category.iconId),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = category.name,
                        )
                    }
                }
            }
        }
    }
}
