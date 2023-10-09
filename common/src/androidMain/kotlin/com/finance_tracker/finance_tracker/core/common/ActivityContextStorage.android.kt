package com.finance_tracker.finance_tracker.core.common

import android.content.Context
import java.lang.ref.WeakReference

actual object ActivityContextStorage {

    private var contextRef: WeakReference<Context> = WeakReference(null)

    fun setContext(context: Context) {
        contextRef  = WeakReference(context)
    }

    fun removeContext() {
        contextRef.clear()
    }

    fun getContext(): Context = contextRef.get() ?: ApplicationContextStorage.applicationContext
}