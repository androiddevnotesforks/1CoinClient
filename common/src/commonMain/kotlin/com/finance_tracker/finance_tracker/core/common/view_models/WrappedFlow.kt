package com.finance_tracker.finance_tracker.core.common.view_models

import com.adeo.kviewmodel.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WrappedFlow<T : Any?>(private val origin: Flow<T>) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable = watchFlow(block)
}

private fun <T> Flow<T>.watchFlow(block: (T) -> Unit): Closeable {
    val context = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    onEach(block).launchIn(context)

    return object : Closeable {
        override fun close() {
            context.cancel()
        }
    }
}