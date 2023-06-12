package com.finance_tracker.finance_tracker.core.ui.drag_and_drop.grid

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.detectReorderAfterLongPress(state: ReorderableState<*>) =
    this.then(
        Modifier.pointerInput(Unit) {
            forEachGesture {
                val down = awaitPointerEventScope {
                    awaitFirstDown(requireUnconsumed = false)
                }
                awaitLongPressOrCancellation(down)?.also {
                    state.interactions.trySend(StartDrag(down.id))
                }
            }
        }
    )