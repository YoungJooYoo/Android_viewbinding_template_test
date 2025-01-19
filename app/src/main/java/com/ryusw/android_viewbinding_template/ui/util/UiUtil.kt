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
    //   - Context 확장 함수: Context 객체에 isRunning() 메서드를 추가하는 확장 함수입니다.
    //   - this: Context -> 현재 호출된 Context 객체를 의미합니다.
    //   - val processInfos = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).runningAppProcesses
    //     - 시스템 서비스를 가져옵니다. (ACTIVITY_SERVICE: 액티비티 관리 서비스)
    //     - ActivityManager: 액티비티 관리 객체를 가져옵니다.
    //     - runningAppProcesses: 실행 중인 프로세스 정보 목록을 가져옵니다.
    //   - processInfos.forEach { processInfo -> ... }
    //     - runningAppProcesses 를 루프를 돌면서 프로세스 정보를 확인합니다.
    //   - if(processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) { ... }
    //     - 중요도가 FOREGROUND(전면)인 프로세스만 검사합니다.
    //   - processInfo.pkgList?.let { it.forEach { pkg -> ... } }
    //     - 프로세스 정보의 패키지 목록을 null 체크를 하여 안전하게 검사합니다.
    //     - 패키지 목록을 루프를 돌면서 현재 앱의 패키지와 일치하는지 확인합니다.
    //   - return true: 일치하는 패키지가 발견되면 앱이 실행 중이라고 판단하여 true 를 반환합니다.
    fun Context.isRunning(): Boolean {
        val processInfos =
            (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).runningAppProcesses
        processInfos.forEach { processInfo ->
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                processInfo.pkgList?.let {
                    it.forEach { pkg ->
                        if (pkg == packageName) return true
                    }
                }
            }
        }
        return false
    }
}