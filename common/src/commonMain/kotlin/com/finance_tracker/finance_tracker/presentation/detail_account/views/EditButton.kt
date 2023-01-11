package com.finance_tracker.finance_tracker.presentation.detail_account.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffoldState
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScopeInstance.road
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.animate
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.rememberCollapsingToolbarScaffoldState
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Composable
fun EditButton(
    modifier: Modifier = Modifier,
    state: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    tint: Color = CoinTheme.color.content,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .statusBarsPadding()
            .padding(
                vertical = state.animate(16.dp, 8.dp),
                horizontal = 16.dp
            )
            .size(
                width = state.animate(84.dp, 50.dp),
                height = state.animate(42.dp, 34.dp)
            )
            .clip(RoundedCornerShape(percent = 50))
            .clickable { onClick.invoke() }
            .road(Alignment.BottomEnd, Alignment.BottomEnd)
            .background(CoinTheme.color.background)
            .padding(vertical = state.animate(12.dp, 8.dp))
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = rememberVectorPainter("ic_edit"),
            tint = tint,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .graphicsLayer {
                    alpha = state.toolbarState.progress
                }
                .padding(start = 4.dp),
            text = stringResource("detail_account_btn_edit"),
            color = tint,
            style = CoinTheme.typography.body1_medium.staticTextSize(),
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}