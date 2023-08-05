package com.finance_tracker.finance_tracker.features.preset_currency

import com.finance_tracker.finance_tracker.core.common.view_models.BaseLocalsStorage
import com.finance_tracker.finance_tracker.core.navigtion.main.MainNavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.core.LaunchFlag

internal fun handleAction(
    action: PresetCurrencyAction,
    baseLocalsStorage: BaseLocalsStorage,
) {
    val rootController = baseLocalsStorage.rootController
    when (action) {
        PresetCurrencyAction.Close -> {
            rootController.popBackStack()
        }
        PresetCurrencyAction.OpenMainScreen -> {
            rootController.push(
                screen = MainNavigationTree.Main.name,
                launchFlag = LaunchFlag.SingleNewTask
            )
        }
    }
}