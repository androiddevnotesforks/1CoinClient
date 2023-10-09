package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.snackbar.SnackbarManager
import com.finance_tracker.finance_tracker.core.theme.CoinPaddings
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.LocalCoinSnackbarHostState
import com.finance_tracker.finance_tracker.core.ui.snackbar.CoinSnackbarHost
import com.finance_tracker.finance_tracker.core.ui.snackbar.CoinSnackbarHostState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.java.KoinJavaComponent

object ScreenState

@Composable
inline fun ComposeScreen(
    component: BaseComponent<*>,
    withBottomNavigation: Boolean = false,
    crossinline content: @Composable (screenState: ScreenState) -> Unit,
) {
    CoinTheme {
        val snackbarManager: SnackbarManager = remember { KoinJavaComponent.get(SnackbarManager::class.java) }
        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState =
            remember(coroutineScope) { CoinSnackbarHostState(coroutineScope) }
        CompositionLocalProvider(LocalCoinSnackbarHostState provides snackbarHostState) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CoinTheme.color.background)
            ) {
                content(ScreenState)

                CoinSnackbarHost(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding(),
                    snackbarHostState = snackbarHostState
                )
            }
        }

        LaunchedEffect(snackbarManager) {
            snackbarManager.observeSnackbar(component.viewModel.screenKey)
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