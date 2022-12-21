package com.finance_tracker.finance_tracker.core.common.pagination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.LoadState
import kotlinx.coroutines.delay

@Suppress("MagicNumber")
@Composable
fun AutoRefreshList(lazyPagingItems: LazyPagingItems<*>) {
    LaunchedEffect(Unit) {
        while (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            delay(10)
        }
        lazyPagingItems.refresh()
    }
}