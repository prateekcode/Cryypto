package com.prateekcode.cryypto.utils

import android.util.Log
import androidx.viewbinding.BuildConfig

fun showLog(message: String?, tag: String = CRYYPTO_CONSTANT) {
    message?.let {
        printFullLog(message, tag)
    }
}

fun Exception.showLog() {
    if (BuildConfig.DEBUG) {
        printStackTrace()
    }
}

fun Throwable.showLog() {
    if (BuildConfig.DEBUG) {
        printStackTrace()
    }
}

private fun printFullLog(message: String, tag: String) {
    if (message.length > 3000) {
        Log.e(tag, message.substring(0, 3000))
        printFullLog(message.substring(3000), tag)
    } else {
        Log.e(tag, message)
    }
}