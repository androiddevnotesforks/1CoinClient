package com.finance_tracker.finance_tracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import com.adeo.kviewmodel.odyssey.setupWithViewModels
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration.DefaultModalConfiguration
import ru.alexgladkov.odyssey.core.configuration.DisplayType
import ru.alexgladkov.odyssey.core.configuration.RootConfiguration

@Composable
fun setupNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    onConfigure: RootController.() -> Unit = {},
    navigationGraph: RootComposeBuilder.() -> Unit = {},
) {
    CoinTheme {
        val rootController = RootComposeBuilder(
            configuration = RootConfiguration(displayType = DisplayType.FullScreen)
        )
            .apply(navigationGraph)
            .build()
            .apply {
                backgroundColor = CoinTheme.color.background
                setupWithViewModels()
                onConfigure.invoke(this)
            }

        CompositionLocalProvider(
            *providers,
            LocalRootController provides rootController
        ) {
            ModalNavigator(
                configuration = DefaultModalConfiguration(
                    displayType = DisplayType.EdgeToEdge
                )
            ) {
                Navigator(startScreen)
            }
        }
    }
}