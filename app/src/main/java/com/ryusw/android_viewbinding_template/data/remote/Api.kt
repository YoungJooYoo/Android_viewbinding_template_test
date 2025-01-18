package com.ryusw.android_viewbinding_template.data.remote

import com.google.gson.Gson
import com.ryusw.android_viewbinding_template.BuildConfig
import com.ryusw.android_viewbinding_template.data.local.prefs.BookSharedPreference
import com.ryusw.android_viewbinding_template.data.remote.interceptor.AddTokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

// 네트워크 관련 코드
object Api {
    // Json으로 정렬
    val gson by lazy { Gson() }
    var ACCESS_TOKEN: String = ""

    private val retrofit by lazy { createRetrofit() }

    private const val BASE_URL = "https://openlibrary.org"
    private const val CONNECTION_TIME = 5000L

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 로그나 Header에 JWT 추가
    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(AddTokenInterceptor(BookSharedPreference))
            .connectTimeout(Duration.ofMillis(CONNECTION_TIME))

        if(BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }


    fun <T> createService(serviceClass : Class<T>) : T {
        return retrofit.create(serviceClass)
    }
}