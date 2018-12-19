package com.gospotcheck.android.mpp


actual class Logger {
    actual fun d(message: String, tag: String) {
        println("$tag : $message")
    }
}