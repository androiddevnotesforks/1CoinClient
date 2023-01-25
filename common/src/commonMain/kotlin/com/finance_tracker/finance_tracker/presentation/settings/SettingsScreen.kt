package com.finance_tracker.finance_tracker.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsCategoriesItem
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsDashboardItem
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsMainCurrencyItem
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsMyProfileItem
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsPrivacyItem
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsScreenTopBar
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsSendingUsageDataItem
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsTelegramChatItem
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsVersionAndUserIdInfo
import dev.icerock.moko.resources.compose.stringResource

private const val TextColorFloat = 0.3f

@Composable
fun SettingsScreen() {
    StoredViewModel<SettingsScreenViewModel> { viewModel ->

        val uriHandler = LocalUriHandler.current
        val clipboardManager = LocalClipboardManager.current

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage,
                uriHandler = uriHandler,
                clipboardManager = clipboardManager,
            )
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .navigationBarsPadding(),
        ) {

            SettingsScreenTopBar(
                onBackClick = viewModel::onBackClick
            )

            Text(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 32.dp
                    ),
                text = stringResource(MR.strings.settings_my_profile),
                style = CoinTheme.typography.subtitle2_medium,
                color = CoinTheme.color.content.copy(alpha = TextColorFloat)
            )

            val userEmail by viewModel.userEmail.collectAsState()
            val isUserAuthorized by viewModel.isUserAuthorized.collectAsState()

            SettingsMyProfileItem(
                userEmail = userEmail,
                isUserAuthorized = isUserAuthorized,
            )

            Divider(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    )
                    .fillMaxWidth()
                    .background(CoinTheme.color.dividers),
            )

            Text(
                modifier = Modifier
                    .padding(
                        top = 24.dp,
                        start = 16.dp
                    ),
                text = stringResource(MR.strings.settings_configuration),
                style = CoinTheme.typography.subtitle2_medium,
                color = CoinTheme.color.content.copy(alpha = TextColorFloat)
            )

            val chosenCurrency by viewModel.chosenCurrency.collectAsState()

            SettingsMainCurrencyItem(
                selectedCurrency = chosenCurrency,
                onCurrencySelect = viewModel::onCurrencySelect,
                onCurrencyClick = viewModel::onCurrencyClick
            )

            SettingsCategoriesItem(
                onClick = viewModel::onCategorySettingsClick
            )

            SettingsDashboardItem(
                onClick = viewModel::onDashboardSettingsClick
            )

            Divider(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .fillMaxWidth()
                    .background(CoinTheme.color.dividers),
            )

            Text(
                modifier = Modifier
                    .padding(
                        top = 24.dp,
                        start = 16.dp
                    ),
                text = stringResource(MR.strings.settings_other),
                style = CoinTheme.typography.subtitle2_medium,
                color = CoinTheme.color.content.copy(alpha = TextColorFloat)
            )

            val isSendingUsageDataEnabled by viewModel.isSendingUsageDataEnabled.collectAsState()

            SettingsSendingUsageDataItem(
                isEnabled = isSendingUsageDataEnabled,
                onChange = viewModel::onSendingUsageDataClick,
                onInfoClick = viewModel::onSendingUsageDataInfoClick
            )

            SettingsPrivacyItem()

            SettingsTelegramChatItem(
                onClick = viewModel::onTelegramCommunityClick
            )

            val userId by viewModel.userId.collectAsState()

            SettingsVersionAndUserIdInfo(
                versionName = viewModel.versionName,
                userId = userId,
                onCopyUserId = viewModel::onCopyUserId
            )
        }
    }

}