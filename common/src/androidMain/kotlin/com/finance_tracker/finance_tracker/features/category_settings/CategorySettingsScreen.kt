package com.finance_tracker.finance_tracker.features.category_settings

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.provideThemeImage
import com.finance_tracker.finance_tracker.core.ui.CategoryCard
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.core.ui.EmptyStub
import com.finance_tracker.finance_tracker.core.ui.ItemWrapper
import com.finance_tracker.finance_tracker.core.ui.drag_and_drop.column.DraggableItem
import com.finance_tracker.finance_tracker.core.ui.drag_and_drop.column.rememberDragDropState
import com.finance_tracker.finance_tracker.domain.models.Category
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.collections.immutable.ImmutableList

private val CategoriesListContentPadding = 16.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CategorySettingsScreen() {
    ComposeScreen<CategorySettingsViewModel> { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage,
                onCancelClick = viewModel::onCancelDeleting,
                onConfirmDeleteClick = viewModel::onConfirmDeleteCategory
            )
        }

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        Column(modifier = Modifier.fillMaxSize()) {

            val selectedTransactionTypeTab by viewModel.selectedTransactionType.collectAsState()

            val pagerState = rememberPagerState()

            CategorySettingsAppBar(
                pagerState = pagerState,
                selectedTransactionTypeTab = selectedTransactionTypeTab,
                onTransactionTypeSelect = viewModel::onTransactionTypeSelect,
                onAddCategoryClick = viewModel::onAddCategoryClick,
                onBackClick = viewModel::onBackClick
            )

            val transactionTypeTabPage by viewModel.transactionTypeTabPage.collectAsState()
            LaunchedEffect(transactionTypeTabPage) {
                pagerState.animateScrollToPage(transactionTypeTabPage)
            }

            LaunchedEffect(pagerState.currentPage) {
                viewModel.onPageChanged(pagerState.currentPage)
            }

            HorizontalPager(
                pageCount = viewModel.transactionTypesCount,
                state = pagerState
            ) { page ->
                val categories by viewModel.getCategories(page).collectAsState()
                if (categories.isEmpty()) {
                    EmptyStub(
                        image = painterResource(
                            provideThemeImage(
                                darkFile = MR.images.categories_empty_dark,
                                lightFile = MR.images.categories_empty_light
                            )
                        ),
                        text = stringResource(MR.strings.add_category),
                        onClick = viewModel::onAddCategoryClick
                    )
                } else {
                    CategoryDragColumn(
                        categories = categories,
                        onSwap = viewModel::swapExpenseCategories,
                        onCrossDeleteClick = viewModel::onDeleteCategoryClick,
                        onClick = viewModel::onCategoryCardClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategoryDragColumn(
    categories: ImmutableList<Category>,
    onClick: (Category) -> Unit,
    onSwap: (Int, Int) -> Unit,
    onCrossDeleteClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    val navigationBarsHeight = LocalFixedInsets.current.navigationBarsHeight

    val listState = rememberLazyListState()
    val dragState = rememberDragDropState(
        listState,
        categories
    ) { fromIndex, toIndex ->
        onSwap(fromIndex, toIndex)
    }

    val itemShape by remember(categories.lastIndex) {
        derivedStateOf {
            { index: Int ->
                when (index) {
                    0 -> {
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                        )
                    }

                    categories.lastIndex -> {
                        RoundedCornerShape(
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    }

                    else -> {
                        RoundedCornerShape(0.dp)
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(
            top = CategoriesListContentPadding,
            start = CategoriesListContentPadding,
            end = CategoriesListContentPadding,
            bottom = CategoriesListContentPadding + navigationBarsHeight
        ),
    ) {
        itemsIndexed(categories, key = { _, item -> item.id }) { index, category ->
            DraggableItem(
                key = category.id,
                dragState = dragState,
                index = index
            ) { isDragging ->
                val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)
                val itemShapeLambda by remember(index) {
                    derivedStateOf { itemShape(index) }
                }
                Card(
                    elevation = elevation,
                    shape = itemShapeLambda
                ) {
                    ItemWrapper(
                        isFirstItem = index == 0,
                        isLastItem = index == categories.lastIndex,
                    ) {
                        val onClickLambda = remember(category) {
                            { onClick(category) }
                        }
                        val onCrossDeleteClickLambda = remember(category) {
                            { onCrossDeleteClick(category) }
                        }
                        val topPadding by remember(index) {
                            derivedStateOf { if (index == 0) 8.dp else 0.dp }
                        }
                        val bottomPadding by remember(index) {
                            derivedStateOf { if (index == categories.lastIndex) 8.dp else 0.dp }
                        }
                        CategoryCard(
                            data = category,
                            modifier = Modifier
                                .padding(
                                    top = topPadding,
                                    bottom = bottomPadding
                                ),
                            onClick = onClickLambda,
                            onCrossDeleteClick = onCrossDeleteClickLambda
                        )
                    }
                }
            }
        }
    }
}
