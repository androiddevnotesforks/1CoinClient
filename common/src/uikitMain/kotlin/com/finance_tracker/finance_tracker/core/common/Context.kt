package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable

abstract class IosContext

actual typealias Context = IosContext

object EmptyContext: IosContext()

@Composable
actual fun getContext(): Context = EmptyContext