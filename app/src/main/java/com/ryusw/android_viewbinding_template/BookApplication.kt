package com.ryusw.android_viewbinding_template

import android.app.Application
import android.content.Context
import com.ryusw.android_viewbinding_template.data.remote.Api
import com.ryusw.android_viewbinding_template.data.remote.service.BookService
import com.ryusw.android_viewbinding_template.data.repository.BookRepository

// 앱 실행 시 제일 먼저 생성 되는 Application class
class BookApplication : Application() {
    // DI를 사용하지 않으므로 Application에서 singleton으로 구현
    val bookRepository by lazy { BookRepository(bookService = Api.createService(BookService::class.java)) }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        // 전역 context 설정
        lateinit var appContext : Context
            private set
    }
}