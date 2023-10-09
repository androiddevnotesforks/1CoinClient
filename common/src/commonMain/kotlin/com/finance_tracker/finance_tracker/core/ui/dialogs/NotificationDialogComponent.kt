package com.finance_tracker.finance_tracker.core.ui.dialogs

import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.resources.desc.StringDesc

class NotificationDialogComponent(
    private val componentContext: ComponentContext,
    val textRes: StringDesc,
    val onOkClick: () -> Unit
): ComponentContext by componentContext
