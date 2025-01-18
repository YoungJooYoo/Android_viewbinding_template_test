package com.ryusw.android_viewbinding_template.ui.util

import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager

object UiUtil {
    // sp -> dp 변경
    fun Int.toDp() = (this * Resources.getSystem().displayMetrics.density).toInt()

    // 키보드 감추기
    fun View.hideKeyboard() {
        val ime = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        ime.hideSoftInputFromWindow(windowToken, 0)
    }

    // 앱 실행 확인
    fun Context.isRunning() : Boolean {
        val processInfos = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).runningAppProcesses
        processInfos.forEach { processInfo ->
            if(processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                processInfo.pkgList?.let {
                    it.forEach { pkg ->
                        if(pkg == packageName) return true
                    }
                }
            }
        }
        return false
    }
}