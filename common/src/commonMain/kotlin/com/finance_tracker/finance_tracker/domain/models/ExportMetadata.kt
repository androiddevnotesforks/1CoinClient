package com.finance_tracker.finance_tracker.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExportMetadata(
    @SerialName("dbVersion")
    val dbVersion: Long
)