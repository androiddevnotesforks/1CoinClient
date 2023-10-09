package com.finance_tracker.finance_tracker.core.common.view_models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.finance_tracker.finance_tracker.core.theme.LocalCoinSnackbarHostState
import com.finance_tracker.finance_tracker.core.ui.snackbar.CoinSnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class BaseLocalsStorage(
    val coinSnackbarHostState: CoinSnackbarHostState?,
    val coroutineScope: CoroutineScope
)

@Composable
inline fun <Action: Any> ComponentViewModel<Action, *>.watchViewActions(
    crossinline onExecute: (action: Action, baseLocalsStorage: BaseLocalsStorage) -> Unit
) {
    val coinSnackbarHostState = LocalCoinSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()
    val baseLocalsStorage = remember(coroutineScope) {
        BaseLocalsStorage(
            coinSnackbarHostState,
            coroutineScope
        )
    }

    LaunchedEffect(Unit) {
        viewActionChannel
            .onEach { action ->
                action ?: return@onEach
                onExecute(action, baseLocalsStorage)
            }
            .launchIn(this)
    }
}