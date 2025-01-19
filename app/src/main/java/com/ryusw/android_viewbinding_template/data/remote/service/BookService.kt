package com.ryusw.android_viewbinding_template.data.remote.service

import com.ryusw.android_viewbinding_template.data.remote.dto.BookDto
import retrofit2.http.GET
import retrofit2.http.Query

// 이 인터페이스는 Retrofit 라이브러리를 사용하여 API 통신을 정의합니다.
// Retrofit은 HTTP API를 쉽게 사용할 수 있도록 도와주는 라이브러리입니다.
// 실제로 API를 호출하는 인터페이스
interface BookService {

    // @GET 어노테이션은 HTTP GET 요청을 나타냅니다.
    // "/search.json"은 API 엔드포인트(endpoint)를 나타냅니다.
    // 즉, API의 기본 URL 뒤에 "/search.json"이 붙어서 실제 요청 URL이 구성됩니다.
    @GET("/search.json")
    suspend fun getBook(
        @Query("q") query: String,
        @Query("language") language: String,
    ): BookDto
}

/*
*
*   @Query 어노테이션은 URL 쿼리 파라미터를 나타냅니다.
     "q"는 쿼리 파라미터의 이름이고, query는 해당 파라미터의 값을 전달받는 변수입니다.
     예를 들어, query가 "kotlin"이면, URL은 "/search.json?q=kotlin"이 됩니다.
        * */