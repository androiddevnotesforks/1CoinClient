package com.finance_tracker.finance_tracker.features.plans.setup

fun SetupPlanFlowState.setCurrentStepForFlowState(
    isExpenseCategoryEmpty: Boolean
) {
    if (isExpenseCategoryEmpty) {
        setStep(SetupPlanStep.Category)
    } else {
        setStep(SetupPlanStep.PrimaryAmount)
    }
}