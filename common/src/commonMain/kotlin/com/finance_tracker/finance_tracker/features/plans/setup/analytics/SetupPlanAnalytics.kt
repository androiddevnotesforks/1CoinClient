package com.finance_tracker.finance_tracker.features.plans.setup.analytics

import com.benasher44.uuid.uuid4
import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Plan
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class SetupPlanAnalytics: BaseAnalytics() {

    override val screenName = "SetupPlanScreen"

    private var actionId = ""
    private var startOfAction: Instant? = null

    fun trackSetupPlanScreenOpen() {
        actionId = uuid4().toString()
        startOfAction = Clock.System.now()
        trackScreenOpen(mapOf(
            ActionIdKey to actionId
        ))
    }

    fun trackCategoryClick() {
        trackClick(
            eventName = "Category",
            properties = mapOf(
                ActionIdKey to actionId
            )
        )
    }

    fun trackPrimaryAmountClick(amountText: String) {
        trackClick(
            eventName = "Amount",
            properties = mapOf(
                ActionIdKey to actionId,
                "amount_text" to amountText
            )
        )
    }

    fun trackCategorySelect(category: Category) {
        trackSelect(
            eventName = "Category",
            properties = mapOf(
                ActionIdKey to actionId,
                "category_name" to category.name
            )
        )
    }

    fun trackAddClick(
        isFromButtonClick: Boolean,
        plan: Plan
    ) {
        if (isFromButtonClick) {
            trackButtonAddClick(plan = plan)
        } else {
            trackMenuAddClick(plan = plan)
        }
    }

    private fun trackButtonAddClick(plan: Plan) {
        val planProperties = plan.getPlanProperties()
        trackClick(
            eventName = "ButtonAdd",
            properties = mapOf(
                ActionIdKey to actionId,
                DurationKey to getActionDuration()
            ) + planProperties
        )
    }

    private fun trackMenuAddClick(plan: Plan) {
        val planProperties = plan.getPlanProperties()
        trackClick(
            eventName = "MenuAdd",
            properties = mapOf(
                ActionIdKey to actionId,
                DurationKey to getActionDuration()
            ) + planProperties
        )
    }

    fun trackEditClick(
        isFromButtonClick: Boolean,
        oldPlan: Plan,
        newPlan: Plan
    ) {
        if (isFromButtonClick) {
            trackButtonEditClick(oldPlan, newPlan)
        } else {
            trackMenuEditClick(oldPlan, newPlan)
        }
    }

    private fun trackButtonEditClick(
        oldPlan: Plan,
        newPlan: Plan
    ) {
        val oldPlanProperties = oldPlan.getPlanProperties(
            keyPrefix = "old_"
        )
        val newPlanProperties = newPlan.getPlanProperties(
            keyPrefix = "new_"
        )
        trackClick(
            eventName = "ButtonEdit",
            properties = mapOf(
                ActionIdKey to actionId,
                DurationKey to getActionDuration()
            ) + oldPlanProperties + newPlanProperties
        )
    }

    private fun trackMenuEditClick(
        oldPlan: Plan,
        newPlan: Plan
    ) {
        val oldPlanProperties = oldPlan.getPlanProperties(
            keyPrefix = "old_"
        )
        val newPlanProperties = newPlan.getPlanProperties(
            keyPrefix = "new_"
        )
        trackClick(
            eventName = "MenuEdit",
            properties = mapOf(
                ActionIdKey to actionId,
                DurationKey to getActionDuration()
            ) + oldPlanProperties + newPlanProperties
        )
    }

    private fun getActionDuration(): Long? {
        return startOfAction?.let { Clock.System.now().minus(it).inWholeMilliseconds }
    }

    private fun Plan.getPlanProperties(keyPrefix: String = ""): Map<String, Any?> {
        return mapOf(
            "${keyPrefix}category_name" to category.name,
            "${keyPrefix}amount_value" to limitAmount.amountValue
        )
    }

    fun trackDeletePlanClick(plan: Plan) {
        val transactionProperties = plan.getPlanProperties()
        trackClick(
            eventName = "Delete",
            properties = mapOf(ActionIdKey to actionId) + transactionProperties
        )
    }

    fun trackRestorePlan() {
        trackClick(eventName = "RestoreExpenseLimit")
    }

    companion object {
        private const val ActionIdKey = "action_id"
        private const val DurationKey = "duration"
    }
}