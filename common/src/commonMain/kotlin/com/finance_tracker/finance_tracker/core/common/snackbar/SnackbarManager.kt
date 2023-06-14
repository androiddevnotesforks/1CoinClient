package com.finance_tracker.finance_tracker.core.common.snackbar

import com.finance_tracker.finance_tracker.core.ui.snackbar.SnackbarState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SnackbarManager {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var checkingPendingModel: Job? = null
    private var pendingSnackbarStateWithScreenParams: SnackbarStateWithScreenParams? = null
    private val snackbarStateWithScreenParams = Channel<SnackbarStateWithScreenParams?>(capacity = 1)

    fun showSnackbar(
        screenKey: String,
        showOnCurrentScreen: Boolean,
        snackbarState: SnackbarState
    ) {
        snackbarStateWithScreenParams.trySend(
            SnackbarStateWithScreenParams(
                snackbarState = snackbarState,
                screenKey = screenKey,
                showOnCurrentScreen = showOnCurrentScreen
            )
        )
    }

    fun hideSnackbar() {
        snackbarStateWithScreenParams.trySend(null)
    }

    fun observeSnackbar(screenKey: String): Flow<SnackbarState?> {
        trySendPendingSnackbarState()
        return snackbarStateWithScreenParams.receiveAsFlow()
            .filter {
                val isSnackbarForCurrentScreen = it == null ||
                        it.screenKey == screenKey && it.showOnCurrentScreen ||
                        it.screenKey != screenKey && !it.showOnCurrentScreen
                if (!isSnackbarForCurrentScreen) {
                    pendingSnackbarStateWithScreenParams = it
                }
                isSnackbarForCurrentScreen
            }
            .onEach { pendingSnackbarStateWithScreenParams = null }
            .map { it?.snackbarState }
    }

    private fun trySendPendingSnackbarState() {
        if (pendingSnackbarStateWithScreenParams == null) return

        checkingPendingModel?.cancel()
        checkingPendingModel = coroutineScope.launch {

            var currentWaitingInMillis = 0L
            while (pendingSnackbarStateWithScreenParams != null) {
                ensureActive()
                val pModel = pendingSnackbarStateWithScreenParams
                if (pModel != null) {
                    snackbarStateWithScreenParams.trySend(pModel)
                    delay(PendingSnackbarAtteptDelay)
                    currentWaitingInMillis += PendingSnackbarAtteptDelay
                }
                if (currentWaitingInMillis >= MaxPendingSnackbarAwaitingInMillis) {
                    pendingSnackbarStateWithScreenParams = null
                }
            }
        }
    }

    companion object {
        private const val MaxPendingSnackbarAwaitingInMillis = 1_800L
        private const val PendingSnackbarAtteptDelay = 300L
    }
}
