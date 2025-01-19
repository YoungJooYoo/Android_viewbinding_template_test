package com.ryusw.android_viewbinding_template.common.logger

import android.util.Log
import com.ryusw.android_viewbinding_template.BuildConfig

// 앱 로깅을 위한 유틸리티 객체
object AppLogger {

    // Verbose 레벨 로그 출력
    fun v(className: String, method: String, message: String) {
        // Log.v()를 사용하여 Verbose 레벨 로그를 출력합니다.
        // className: 로그 태그로 사용될 클래스 이름
        // "$method() -> $message": 로그 메시지 (메서드 이름과 메시지를 포함)
        Log.v(className, "$method() -> $message")
    }

    // Debug 레벨 로그 출력
    fun d(className: String, method: String, message: String) {
        // BuildConfig.DEBUG가 false이면 (릴리즈 빌드인 경우) 로그를 출력하지 않고 함수를 종료합니다.
        if (!BuildConfig.DEBUG) {
            return
        }
        // BuildConfig.DEBUG가 true이면 (디버그 빌드인 경우) Log.d()를 사용하여 Debug 레벨 로그를 출력합니다.
        Log.d(className, "$method() -> $message")
    }

    // Info 레벨 로그 출력
    fun i(className: String, method: String, message: String) {
        // Log.i()를 사용하여 Info 레벨 로그를 출력합니다.
        Log.i(className, "$method() -> $message")
    }

    // Warning 레벨 로그 출력
    fun w(className: String, method: String, message: String) {
        // Log.w()를 사용하여 Warning 레벨 로그를 출력합니다.
        Log.w(className, "$method() -> $message")
    }

    // Error 레벨 로그 출력
    fun e(className: String, method: String, message: String, throwable: Throwable? = null) {
        // Log.e()를 사용하여 Error 레벨 로그를 출력합니다.
        Log.e(className, "$method() -> $message")
        // BuildConfig.DEBUG가 false이면 (릴리즈 빌드인 경우) 예외 스택 트레이스를 출력합니다.
        if (!BuildConfig.DEBUG) {
            throwable?.printStackTrace()
        }
    }
}