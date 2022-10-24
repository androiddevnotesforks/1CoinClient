package com.finance_tracker.finance_tracker.presentation.add_category

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedTextField

@Composable
fun AddCategoryScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        AddCategoryAppBar()

        Row {
            CoinOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            )
        }
    }

}