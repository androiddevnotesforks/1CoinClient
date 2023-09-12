package com.finance_tracker.finance_tracker.core.common

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun Context.shareFile(uri: String, type: String) {
    val intent = buildShareIntent(uri, type)
    startActivity(intent)
}

private fun buildShareIntent(uri: String, type: String): Intent {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_STREAM, uri.toUri())
        this.type = type
    }
    return Intent.createChooser(sendIntent, null)
}