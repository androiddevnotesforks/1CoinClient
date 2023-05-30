package com.finance_tracker.finance_tracker.core.navigation

import ru.alexgladkov.odyssey.compose.RootController

inline fun <reified T> RootController.currentScreenParams(): T? {
    return currentScreen.value?.screen?.params as? T
}

inline fun <reified T> RootController.setScreenResult(block: T.() -> Unit) {
    currentScreenParams<T>()?.block()
}