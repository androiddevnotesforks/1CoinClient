package com.finance_tracker.finance_tracker.features.plans.overview.views.set_limit.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedTextField
import com.finance_tracker.finance_tracker.core.ui.currency.CurrencyView
import com.finance_tracker.finance_tracker.domain.models.Currency
import dev.icerock.moko.resources.compose.stringResource

private const val AmountCharsLimit = 24

@Suppress("ReusedModifierInstance")
@Composable
internal fun AmountTextField(
    enteredBalance: TextFieldValue,
    isError: Boolean,
    currency: Currency,
    onAmountChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalTextInputService provides null) {
        CoinOutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            value = enteredBalance,
            onValueChange = onAmountChange,
            label = {
                Text(
                    text = stringResource(MR.strings.plans_amount_subtitle),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            placeholder = {
                Text(
                    text = stringResource(MR.strings.new_account_field_amount_placeholder),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = CoinTheme.typography.body1.staticTextSize()
                )
            },
            trailingIcon = {
                CurrencyView(
                    modifier = Modifier.padding(end = 16.dp),
                    currency = currency
                )
            },
            singleLine = true,
            isError = isError,
            maxLines = 1,
            charsLimit = AmountCharsLimit,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}