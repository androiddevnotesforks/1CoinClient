package com.finance_tracker.finance_tracker.features.ios_interaction_sample

import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// TODO -- Delete after ViewModel consuming from iOS debugging
@Suppress("ClassOrdering")
class SampleViewModel: BaseViewModel<SampleAction>() {
    private val counterIncrement = 42

    // TODO -(Done)- listen StateFlow with primitives
    // TODO -(Done)- call methods without arguments to modify primitive StateFlow
    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    fun increaseCounter() {
        _counter.update {
            it + 1
        }
    }

    // TODO -(Done)- listen StateFlow with data class
    // TODO -(Done)- call methods with data class argument to modify data class StateFlow
    private val _textFieldParams = MutableStateFlow(TextFieldParams())
    val textFieldParams = _textFieldParams.asStateFlow()

    fun setTextFieldValue(textFieldParams: TextFieldParams) {
        _textFieldParams.update {
            it.copy(
                text = textFieldParams.text,
                colorName = textFieldParams.colorName
            )
        }
    }

    // TODO -(Done)- onScreenComposed call
    fun onScreenComposed() {
        _counter.update { it + counterIncrement }
        _textFieldParams.update { TextFieldParams(text = "Initial Amazing Text", colorName = "White") }
    }

    // TODO -(Done)- listen for actions sealed class. moko-kswift may be needed
    // TODO -(Done)- call methods to modify StateFlow with action

    fun emitNavigateBackAction() {
        viewAction = SampleAction.NavigateBack
    }

    fun emitOpenDetailsScreenAction(navScreenParams: NavScreenParams) {
        viewAction = SampleAction.OpenDetailsScreen(navScreenParams)
    }
}