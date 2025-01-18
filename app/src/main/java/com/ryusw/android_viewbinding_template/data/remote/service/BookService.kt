package com.ryusw.android_viewbinding_template.data.remote.service

import com.ryusw.android_viewbinding_template.data.remote.dto.BookDto
import retrofit2.http.GET
import retrofit2.http.Query

// 실제로 API를 호출하는 인터페이스
interface BookService {
    @GET("/search.json")
    suspend fun getBook(
        @Query("q") query : String,
        @Query("language")language : String,
    ) : BookDto
}