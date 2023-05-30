package com.finance_tracker.finance_tracker.core.ui.snackbar

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

// SnackbarFadeOutMillis is copy-pasted from `androidx.compose.material.SnackbarHost`
private const val SnackbarFadeOutMillis = 75L

@Stable
class CoinSnackbarHostState(
    private val coroutineScope: CoroutineScope
) {

    val baseSnackbarHostState = SnackbarHostState()
    private var snackbarJob: Job? = null

    var currentSnackbarState by mutableStateOf<SnackbarState?>(null)
        private set
    var bottomPadding by mutableStateOf(0.dp)
        private set

    init {
        snapshotFlow { baseSnackbarHostState.currentSnackbarData }
            .onEach {
                if (baseSnackbarHostState.currentSnackbarData == null) {
                    delay(SnackbarFadeOutMillis)
                    if (baseSnackbarHostState.currentSnackbarData == null) {
                        currentSnackbarState = null
                    }
                }
            }
            .launchIn(coroutineScope)
    }

    fun handleSnackbarState(snackbarState: SnackbarState?) {
        if (snackbarState != null) {
            showSnackbar(snackbarState)
        } else {
            dismiss()
        }
    }

    private fun showSnackbar(
        snackbarState: SnackbarState
    ) {
        snackbarJob?.cancel()
        snackbarJob = coroutineScope.launch {
            showSnackbarWithResult(snackbarState)
        }
    }

    private suspend fun showSnackbarWithResult(
        snackbarState: SnackbarState
    ): SnackbarResult? {
        currentSnackbarState = snackbarState
        return try {
            baseSnackbarHostState.showSnackbar("")
        } finally {
            baseSnackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    fun setSnackbarHostPadding(bottom: Dp) {
        bottomPadding = bottom
    }

    private fun dismiss() {
        snackbarJob?.cancel()
        snackbarJob = null
    }
}