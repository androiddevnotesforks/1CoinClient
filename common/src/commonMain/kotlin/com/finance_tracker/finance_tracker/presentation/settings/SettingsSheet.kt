package com.finance_tracker.finance_tracker.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsSheet(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 220.dp,
        sheetContent = {



    }) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .navigationBarsPadding()
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            bottom = 16.dp
                        )
                        .align(Alignment.CenterHorizontally),
                    text = stringResource("settings_top_text"),
                    style = CoinTheme.typography.h4
                )
                Divider(
                    color = CoinTheme.color.dividers,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                ) {
                    Icon(
                        painter = rememberVectorPainter(id = "ic_category_1"),
                        contentDescription = null,
                        modifier = Modifier
                            //.size(20.dp)
                            .padding(
                                start = 16.dp,
                                end = 8.dp
                            ),
                        tint = CoinTheme.color.content
                    )
                    Text(
                        text = stringResource("settings_main_currency"),
                        style = CoinTheme.typography.body1
                    )
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}