package com.finance_tracker.finance_tracker.core.ui.drag_and_drop.grid

import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerId
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.isOutOfBounds
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

internal suspend fun PointerInputScope.awaitLongPressOrCancellation(
    initialDown: PointerInputChange
): PointerInputChange? {
    var longPress: PointerInputChange? = null
    var currentDown = initialDown
    val longPressTimeout = viewConfiguration.longPressTimeoutMillis
    return try {
        withTimeout(longPressTimeout) {
            awaitPointerEventScope {
                var finished = false
                while (!finished) {
                    val event = awaitPointerEvent(PointerEventPass.Main)
                    if (event.changes.all { it.changedToUpIgnoreConsumed() }) {
                        finished = true
                    }

                    if (
                        event.changes.any {
                            it.isConsumed || it.isOutOfBounds(size, extendedTouchPadding)
                        }
                    ) {
                        finished = true
                    }

                    val consumeCheck = awaitPointerEvent(PointerEventPass.Final)
                    if (consumeCheck.changes.any { it.isConsumed }) {
                        finished = true
                    }
                    if (!event.isPointerUp(currentDown.id)) {
                        longPress = event.changes.firstOrNull { it.id == currentDown.id }
                    } else {
                        val newPressed = event.changes.firstOrNull { it.pressed }
                        if (newPressed != null) {
                            currentDown = newPressed
                            longPress = currentDown
                        } else {
                            finished = true
                        }
                    }
                }
            }
        }
        null
    } catch (_: TimeoutCancellationException) {
        longPress ?: initialDown
    }
}

private fun PointerEvent.isPointerUp(pointerId: PointerId): Boolean =
    changes.firstOrNull { it.id == pointerId }?.pressed != true
