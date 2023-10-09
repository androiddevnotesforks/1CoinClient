package com.finance_tracker.finance_tracker.core.common.decompose_ext

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigationSource
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.KSerializer

inline fun <reified C : Any, T : Any> ComponentContext.bottomDialogSlot(
    source: SlotNavigationSource<C>,
    serializer: KSerializer<C>?,
    noinline childFactory: (configuration: C, ComponentContext) -> T,
): Value<ChildSlot<C, T>> = childSlot(
    source = source,
    serializer = serializer,
    handleBackButton = true,
    key = "BottomDialogSlot",
    childFactory = childFactory
)

inline fun <reified C : Any, T : Any> ComponentContext.alertDialogSlot(
    source: SlotNavigationSource<C>,
    serializer: KSerializer<C>?,
    noinline childFactory: (configuration: C, ComponentContext) -> T,
): Value<ChildSlot<C, T>> = childSlot(
    source = source,
    serializer = serializer,
    handleBackButton = true,
    key = "AlertDialogSlot",
    childFactory = childFactory
)