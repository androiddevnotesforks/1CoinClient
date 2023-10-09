package com.finance_tracker.finance_tracker.core.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.imePadding
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun AlertDialogSurface(content: @Composable () -> Unit) {
    DialogSurface(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CoinTheme.color.background),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialogSurface(
    onDismissRequest: () -> Unit,
    closeable: Boolean = true,
    content: @Composable () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { closeable }
    )
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = CoinTheme.color.backgroundSurface,
        contentColor = CoinTheme.color.content,
        sheetState = bottomSheetState,
        windowInsets = WindowInsets
            .systemBars
            .only(WindowInsetsSides.Horizontal)
    ) {
        DialogSurface(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding(),
            content = { content() }
        )
    }
}

@Composable
@Suppress("ReusedModifierInstance")
private fun DialogSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CoinTheme {
        Surface(
            modifier = modifier,
            color = CoinTheme.color.backgroundSurface
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}