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

// 네트워크 관련 코드를 관리하는 객체
object Api {
    val gson by lazy { Gson() } // JSON 데이터 변환을 위한 Gson 객체
    var ACCESS_TOKEN: String = "" // API 인증 토큰 (액세스 토큰)

    // Retrofit 클라이언트 (Retrofit 객체를 생성하는 데 사용됨)
    private val retrofit by lazy { createRetrofit() }

    // 베이스 URL (API 서버의 기본 주소)
    private const val BASE_URL = "https://openlibrary.org"

    // 연결 타임아웃 시간 (밀리초 단위)
    private const val CONNECTION_TIME = 5000L

    // Retrofit 객체 생성 (private으로 선언되어 외부에서 직접 접근 불가능)
    private fun createRetrofit(): Retrofit {
        val okHttpClient = createOkHttpClient()  // OkHttp 클라이언트 생성

        return Retrofit.Builder()
            .baseUrl(BASE_URL) // rest 제외 후, 서버통신 가능한 URL
            .client(okHttpClient)  // OkHttp 클라이언트 설정
            .addConverterFactory(GsonConverterFactory.create(gson))  // JSON 변환기 설정, json을 컨버터 gson 사용, 인코더 디코더 다함
            .build()
    }

    // OkHttp 클라이언트 생성 (private으로 선언되어 외부에서 직접 접근 불가능)
    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // JWT 토큰 추가 인터셉터 설정, 통신을 3자가 api를 부르면 안되니, jwt -> json 웹토큰
        // 간단하 "회원정보", jwt 디코더가 많다 종류가. 알고리즘 암호화 ,sha256??
        // jwt 디코더 후, 회원정보 맞춰보고, 맞으면 api가져가!!!
        // 인터셉터 :: 통신을 가로채서 추가작업
        builder.addInterceptor(AddTokenInterceptor(BookSharedPreference))

        // 연결 타임아웃 설정, 대부분 5초가 최대
        builder.connectTimeout(Duration.ofMillis(CONNECTION_TIME))

        // 로그 레벨 설정 (디버그 모드일 때만 HttpLoggingInterceptor 추가)
        // 모든거 다 까보는 디버그 모드,
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }

        return builder.build()
    }

    // 서비스 생성 (외부에서 API 서비스 객체 생성을 위한 메서드) , 레트로핏, 인스턴스화 시켜준다. 통신할 수 있도록 만듬
    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}