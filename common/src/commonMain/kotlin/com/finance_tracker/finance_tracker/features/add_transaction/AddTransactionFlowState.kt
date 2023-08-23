package com.finance_tracker.finance_tracker.features.add_transaction

import kotlinx.coroutines.flow.MutableStateFlow

class AddTransactionFlowState(
    val flow: AddTransactionFlow
) {
    var currentStepIndex = -1
        private set
    val currentStepFlow = MutableStateFlow(getCurrentStep(InitialCurrentStepIndex))
    val hasPreviousStepFlow = MutableStateFlow(hasPreviousStep(InitialCurrentStepIndex))

    fun previous() {
        val previousIndex = currentStepIndex - 1
        if (previousIndex < 0) return

        setCurrentStepIndex(previousIndex)
    }

    fun next() {
        val nextIndex = currentStepIndex + 1
        if (nextIndex >= flow.steps.size) return

        setCurrentStepIndex(nextIndex)
    }

    fun setStep(step: EnterTransactionStep?) {
        val index = flow.steps.indexOf(step)
        setCurrentStepIndex(index)
    }

    fun setStepAfter(step: EnterTransactionStep) {
        val nextStepIndex = flow.steps.indexOf(step) + 1
        if (nextStepIndex in flow.steps.indices) {
            setCurrentStepIndex(nextStepIndex)
        }
    }

    private fun setCurrentStepIndex(stepIndex: Int) {
        currentStepIndex = stepIndex
        currentStepFlow.value = getCurrentStep(stepIndex)
        hasPreviousStepFlow.value = hasPreviousStep(stepIndex)
    }

    private fun getCurrentStep(stepIndex: Int): EnterTransactionStep? {
        return flow.steps.getOrNull(stepIndex)
    }

    private fun hasPreviousStep(stepIndex: Int): Boolean {
        return stepIndex > 0
    }

    companion object {

        private const val InitialCurrentStepIndex = -1

        fun createNewStatesForAllFlow(): Map<AddTransactionFlow, AddTransactionFlowState> {
            return AddTransactionFlow.values()
                .associateWith { AddTransactionFlowState(it) }
        }
    }
}