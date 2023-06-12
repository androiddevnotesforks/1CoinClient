package com.finance_tracker.finance_tracker.core.ui.drag_and_drop.grid

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

interface DragCancelledAnimation {
    val position: ItemPosition?
    val offset: Offset

    suspend fun dragCancelled(position: ItemPosition, offset: Offset)
}

class SpringDragCancelledAnimation(private val stiffness: Float = Spring.StiffnessMediumLow) : DragCancelledAnimation {
    private val animatable = Animatable(Offset.Zero, Offset.VectorConverter)
    override val offset: Offset
        get() = animatable.value

    override var position by mutableStateOf<ItemPosition?>(null)
        private set

    override suspend fun dragCancelled(position: ItemPosition, offset: Offset) {
        this.position = position
        animatable.snapTo(offset)
        animatable.animateTo(
            Offset.Zero,
            spring(stiffness = stiffness, visibilityThreshold = Offset.VisibilityThreshold)
        )
        this.position = null
    }
}