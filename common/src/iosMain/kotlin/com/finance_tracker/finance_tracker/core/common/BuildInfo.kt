package com.finance_tracker.finance_tracker.core.common

actual object BuildInfo {
    actual val isDebug: Boolean
        get() = Platform.isDebugBinary

    @Suppress("NotImplementedDeclaration")
    actual val buildType: BuildType
        get() = BuildType.Debug //TODO
}