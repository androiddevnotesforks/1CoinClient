package com.finance_tracker.finance_tracker.core.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun AlertDialogSurface(content: @Composable () -> Unit) {
    DialogSurface(
        withDragBar = false,
        hasInnerInsets = false,
        content = content
    )
}

@Composable
fun BottomSheetDialogSurface(
    withDragBar: Boolean = true,
    content: @Composable () -> Unit
) {
    DialogSurface(
        withDragBar = withDragBar,
        hasInnerInsets = true,
        content = content
    )
}

@Composable
private fun DialogSurface(
    withDragBar: Boolean = true,
    hasInnerInsets: Boolean = true,
    content: @Composable () -> Unit
) {
    CoinTheme {
        Surface(
            color = CoinTheme.color.backgroundSurface
        ) {
            Column(
                modifier = Modifier.`if`(hasInnerInsets) {
                    navigationBarsPadding()
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (withDragBar) {
                    Divider(
                        modifier = Modifier
                            .padding(top = 6.dp, bottom = 8.dp)
                            .size(width = 16.dp, height = 2.dp)
                            .clip(CircleShape)
                            .background(CoinTheme.color.content)
                    )
                }
                content()
            }
        }
    }
}