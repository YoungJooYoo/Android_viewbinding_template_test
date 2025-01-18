package com.ryusw.android_viewbinding_template.common.logger
import android.util.Log
import com.ryusw.android_viewbinding_template.BuildConfig

object AppLogger {
    fun v(className: String, method : String, message : String) {
        Log.v(className, "$method() -> $message")
    }

    fun d(className: String, method : String, message : String) {
        if (!BuildConfig.DEBUG) {
            return
        }
        Log.d(className, "$method() -> $message")
    }

    fun i(className: String, method : String, message : String) {
        Log.i(className, "$method() -> $message")
    }

    fun w(className: String, method : String, message : String) {
        Log.w(className, "$method() -> $message")
    }

    fun e(className: String, method : String, message : String, throwable: Throwable? = null) {
        Log.e(className, "$method() -> $message")
        if (!BuildConfig.DEBUG) {
            throwable?.printStackTrace()
        }
    }
}