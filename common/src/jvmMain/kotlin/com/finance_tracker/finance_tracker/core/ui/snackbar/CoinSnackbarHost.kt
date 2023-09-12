package com.finance_tracker.finance_tracker.core.ui.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CoinSnackbarHost(
    snackbarHostState: CoinSnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = snackbarHostState.bottomPadding),
        hostState = snackbarHostState.baseSnackbarHostState
    ) {
        val snackbarState = snackbarHostState.currentSnackbarState
        if (snackbarState != null) {
            CoinSnackbar(snackbarState = snackbarState)
        }
    }
}