package com.finance_tracker.finance_tracker.core.ui.dialogs

import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.resources.desc.StringDesc

class DeleteDialogComponent(
    private val componentContext: ComponentContext,
    val titleRes: StringDesc,
    val onCancelClick: () -> Unit,
    val onDeleteClick: () -> Unit,
): ComponentContext by componentContext
