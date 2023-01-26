package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.domain.models.DashboardWidget
import com.financetracker.financetracker.data.DashboardItemsEntity
import com.financetracker.financetracker.data.DashboardItemsEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DashboardSettingsRepository(
    private val dashboardItemsEntityQueries: DashboardItemsEntityQueries
) {

    fun saveDashboardWidgetState(id: Int, isEnabled: Boolean) {
        dashboardItemsEntityQueries.updateDashboardItemState(
            id = id.toLong(),
            isEnabled = isEnabled
        )
    }

    fun getDashboardWidgets(): Flow<List<DashboardWidget>> {
        return dashboardItemsEntityQueries.getDashboardItems()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { items ->
                getDashboardWidgets(
                    dashboardItemsEntityMap = items.associateBy { it.id.toInt() }
                )
            }
    }

    private fun getDashboardWidgets(
        dashboardItemsEntityMap: Map<Int, DashboardItemsEntity>
    ): List<DashboardWidget> {
        return DashboardWidget.all.map {
            val item = dashboardItemsEntityMap[it.id] ?: return@map it
            return@map it.copy(isEnabled = item.isEnabled)
        }
    }
}