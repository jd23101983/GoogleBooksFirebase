package com.bigbang.googlebooksfirebase.util

import android.util.Log
import com.bigbang.googlebooksfirebase.util.Constants.ERROR_PREFIX
import com.bigbang.googlebooksfirebase.util.Constants.TAG

object DebugLogger {
    @JvmStatic
    fun logError(throwable: Throwable) {
        Log.d(TAG, ERROR_PREFIX + throwable.localizedMessage)
    }

    @JvmStatic
    fun logDebug(message: String?) {
        Log.d(TAG, message)
    }
}