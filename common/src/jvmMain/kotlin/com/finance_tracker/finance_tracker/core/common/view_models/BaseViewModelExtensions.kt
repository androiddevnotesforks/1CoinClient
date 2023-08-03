package com.finance_tracker.finance_tracker.core.common.view_models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.theme.LocalCoinSnackbarHostState
import com.finance_tracker.finance_tracker.core.ui.snackbar.CoinSnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController

data class BaseLocalsStorage(
    val rootController: RootController,
    val context: Context,
    val coroutineScope: CoroutineScope,
    val coinSnackbarHostState: CoinSnackbarHostState?
)

@Composable
inline fun <Action: Any> BaseViewModel<Action>.watchViewActions(
    crossinline onExecute: (action: Action, baseLocalsStorage: BaseLocalsStorage) -> Unit
) {
    val rootController = LocalRootController.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val coinSnackbarHostState = LocalCoinSnackbarHostState.current
    val baseLocalsStorage = remember(rootController, context, coroutineScope) {
        BaseLocalsStorage(
            rootController,
            context,
            coroutineScope,
            coinSnackbarHostState
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