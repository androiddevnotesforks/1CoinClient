package com.finance_tracker.finance_tracker.presentation.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.navigation.TabNavGraph
import com.finance_tracker.finance_tracker.core.ui.CategoryCard
import com.finance_tracker.finance_tracker.core.ui.ExpenseIncomeTabs
import com.finance_tracker.finance_tracker.domain.models.Category
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.koin.androidx.compose.getViewModel

@TabNavGraph
@Destination
@Composable
fun CategorySettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: CategorySettingsScreenViewModel = getViewModel()
) {

    //TODO ФЕЙКОВЫЕ ВРЕМЕННЫЕ ДАННЫЕ
    val categories = listOf(
        Category(
            id = 0,
            name = "Shopping",
            iconId = "ic_category_9",
        ),
        Category(
            id = 1,
            name = "Shopping",
            iconId = "ic_category_9",
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        CategorySettingsAppBar(navigator)
        
        ExpenseIncomeTabs(
            modifier = Modifier
                .padding(
                    top = 24.dp,
                    start = 20.dp
                ),
            navigator = navigator
        )

        LazyColumn(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp
                )
                .fillMaxHeight(),
            contentPadding = PaddingValues(bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(categories) {category ->
                CategoryCard(data = category)
            }
        }
    }
}

@Preview
@Composable
fun CategorySettingsScreenPreview() {
    CategorySettingsScreen(navigator = EmptyDestinationsNavigator)
}