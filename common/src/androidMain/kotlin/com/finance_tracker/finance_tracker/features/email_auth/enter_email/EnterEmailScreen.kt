package com.finance_tracker.finance_tracker.features.email_auth.enter_email

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.imePadding
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.toTextFieldValue
import com.finance_tracker.finance_tracker.core.common.toUiTextFieldValue
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedTextField
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.core.ui.button.PrimaryButton
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun EnterEmailScreen() {
    ComposeScreen<EnterEmailViewModel> { viewModel ->

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(action, baseLocalsStorage)
        }

        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EnterEmailAppBar(
                modifier = Modifier
                    .statusBarsPadding(),
                onBackClick = viewModel::onBackClick
            )

            Spacer(modifier = Modifier.height(72.dp))

            Text(
                text = stringResource(MR.strings.enter_email_title),
                style = CoinTheme.typography.h4,
                textAlign = TextAlign.Center
            )

            val emailTextFieldValue by viewModel.emailTextFieldValue.collectAsState()
            val emailUiTextFieldValue by remember(emailTextFieldValue) {
                derivedStateOf { emailTextFieldValue.toUiTextFieldValue() }
            }

            val isEmailTextFieldReadOnly by viewModel.isEmailTextFieldReadOnly.collectAsState()
            CoinOutlinedTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(
                        top = 24.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                value = emailUiTextFieldValue,
                onValueChange = { uiTextFieldValue ->
                    viewModel.onEmailChange(uiTextFieldValue.toTextFieldValue())
                },
                readOnly = isEmailTextFieldReadOnly,
                label = {
                    Text(
                        text = stringResource(MR.strings.enter_email_field_email_label),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.weight(1f))

            val isContinueEnabled by viewModel.isContinueEnabled.collectAsState()
            val isLoadingButton by viewModel.isLoadingButton.collectAsState()
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                text = stringResource(MR.strings.enter_email_btn_continue),
                enabled = isContinueEnabled,
                loading = isLoadingButton,
                onClick = viewModel::onContinueClick
            )
        }
    }
}

@Composable
private fun EnterEmailAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        AppBarIcon(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterStart),
            painter = painterResource(MR.images.ic_arrow_back),
            onClick = onBackClick,
        )

        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(
                    width = 92.1.dp,
                    height = 22.3.dp
                ),
            painter = painterResource(MR.images.ic_logo_1coin),
            contentDescription = null,
            tint = CoinTheme.color.primary
        )
    }
}