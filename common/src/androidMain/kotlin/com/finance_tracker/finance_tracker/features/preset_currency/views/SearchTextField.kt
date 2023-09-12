package com.finance_tracker.finance_tracker.features.preset_currency.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedTextField
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SearchTextField(
    searchQuery: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    CoinOutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp
            ),
        value = searchQuery,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(MR.strings.preset_currency_search_label),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}