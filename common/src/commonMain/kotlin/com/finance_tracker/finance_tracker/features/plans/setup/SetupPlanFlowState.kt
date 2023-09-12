package com.finance_tracker.finance_tracker.features.plans.setup

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

data class SetupPlanFlowState(
    val steps: List<SetupPlanStep>
) {
    private val currentStepIndex = MutableStateFlow(-1)
    val currentStepFlow = currentStepIndex.map { steps.getOrNull(it) }
    val hasPreviousStep = MutableStateFlow(currentStepIndex.value > 0)

    fun previous() {
        val previousIndex = currentStepIndex.value - 1
        if (previousIndex < 0) return

        setCurrentStepIndex(previousIndex)
    }

    fun next() {
        val nextIndex = currentStepIndex.value + 1
        if (nextIndex >= steps.size) return

        setCurrentStepIndex(nextIndex)
    }

    fun setStep(step: SetupPlanStep?) {
        val index = steps.indexOf(step)
        setCurrentStepIndex(index)
    }

    fun setStepAfter(step: SetupPlanStep) {
        val nextStepIndex = steps.indexOf(step) + 1
        if (nextStepIndex in steps.indices) {
            setCurrentStepIndex(nextStepIndex)
        }
    }

    private fun setCurrentStepIndex(step: Int) {
        currentStepIndex.value = step
        hasPreviousStep.value = step > 0
    }
}