package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun HomeTopBar() {
    TopAppBar(
        backgroundColor = CoinTheme.color.primaryVariant,
        title = {
            Column {
                Text(
                    text = "$42 520",
                    style = CoinTheme.typography.h4
                )
                Text(
                    text = stringResource(R.string.home_topbar_text),
                    style = CoinTheme.typography.subtitle2
                )
            }
        },
        actions = {
            Icon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = null,
                Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        color = CoinTheme.color.secondaryBackground,
                        shape = CircleShape
                    )
                    .clickable { }
                    .padding(8.dp),
                tint = CoinTheme.color.content.copy(alpha = 0.8f)
            )
        },
    )
}

@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBar()
}
