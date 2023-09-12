package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.snackbar.SnackbarManager
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.LocalCoinSnackbarHostState
import com.finance_tracker.finance_tracker.core.ui.snackbar.CoinSnackbarHost
import com.finance_tracker.finance_tracker.core.ui.snackbar.CoinSnackbarHostState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.parameter.ParametersDefinition
import org.koin.java.KoinJavaComponent.get

object ScreenState

@Composable
inline fun <reified T : BaseViewModel<*>> ComposeScreen(
    withBottomNavigation: Boolean = false,
    noinline parameters: ParametersDefinition? = null,
    crossinline block: @Composable (screenState: ScreenState, viewModel: T) -> Unit
) {
    CoinTheme {
        StoredViewModel<T>(parameters = parameters) { viewModel ->
            val snackbarManager: SnackbarManager = remember { get(SnackbarManager::class.java) }
            val coroutineScope = rememberCoroutineScope()
            val snackbarHostState =
                remember(coroutineScope) { CoinSnackbarHostState(coroutineScope) }
            CompositionLocalProvider(LocalCoinSnackbarHostState provides snackbarHostState) {
                Box(
                    modifier = Modifier.background(CoinTheme.color.background)
                ) {
                    block(ScreenState, viewModel)

                    CoinSnackbarHost(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding(),
                        snackbarHostState = snackbarHostState
                    )
                }
            }

            LaunchedEffect(snackbarManager) {
                snackbarManager.observeSnackbar(viewModel.screenKey)
                    .onEach(snackbarHostState::handleSnackbarState)
                    .launchIn(this)
            }

            LaunchedEffect(Unit) {
                val snackbarBottomPadding = if (withBottomNavigation) {
                    CoinPaddings.bottomNavigationBar
                } else {
                    16.dp
                }
                snackbarHostState.setSnackbarHostPadding(snackbarBottomPadding)
            }
        }
    }
}

@Composable
inline fun <reified T : BaseViewModel<*>> ComposeScreen(
    withBottomNavigation: Boolean = false,
    noinline parameters: ParametersDefinition? = null,
    crossinline block: @Composable (viewModel: T) -> Unit
) {
    ComposeScreen<T>(
        withBottomNavigation = withBottomNavigation,
        parameters = parameters
    ) { _, viewModel ->
        block(viewModel)
    }
}