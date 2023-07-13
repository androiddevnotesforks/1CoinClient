package com.finance_tracker.finance_tracker.data.database.helpers

interface EntityDataRetriever<T: Any> {

    fun fieldValues(entity: T): List<String>

    fun columns(): List<String>
}