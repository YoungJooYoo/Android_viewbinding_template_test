package com.ryusw.android_viewbinding_template.data.remote.interceptor

import com.ryusw.android_viewbinding_template.data.local.prefs.BookSharedPreference
import com.ryusw.android_viewbinding_template.data.remote.Api
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

// 소셜로그인 이후 Header에 JWT를 넣어달라고 함
// 통신 할 때 마다 이를 자동으로 넣어주는 코드
// 현재는 사용하지 않음
class AddTokenInterceptor(
    private val testPrefs : BookSharedPreference
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        // 인메모리 데이터로 가지고 있음
        Api.ACCESS_TOKEN.ifEmpty {
            runBlocking {
                Api.ACCESS_TOKEN = testPrefs.query
            }
        }

        builder.addHeader("Authorization", "Bearer ${Api.ACCESS_TOKEN}")
        return chain.proceed(builder.build())
    }
}