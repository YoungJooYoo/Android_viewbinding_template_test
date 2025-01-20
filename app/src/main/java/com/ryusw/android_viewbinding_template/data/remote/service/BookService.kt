package com.ryusw.android_viewbinding_template.data.remote.service

import com.ryusw.android_viewbinding_template.data.remote.dto.BookDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

// 실제 통신되는 인터페이스 - 내부 외부 연결이 이 부분이다. -> 클래스화도 시켜서 사용한다.

// 이 인터페이스는 Retrofit 라이브러리를 사용하여 API 통신을 정의합니다.
// Retrofit은 HTTP API를 쉽게 사용할 수 있도록 도와주는 라이브러리입니다.
// 실제로 API를 호출하는 인터페이스
interface BookService {

    // @GET 어노테이션은 HTTP GET 요청을 나타냅니다.
    // "/search.json"은 API 엔드포인트(endpoint)를 나타냅니다.
    // 즉, API의 기본 URL 뒤에 "/search.json"이 붙어서 실제 요청 URL이 구성됩니다.
    @GET("/search.json/{page}") // http get사용, 가져오기
    suspend fun getBook(
        @Query("q") query: String,
        @Query("language") language: String,
       //  @Path("page") page : Int // 파라미터 식느낌으로,
    ): BookDto

    @POST("/search.json") // 보내기 @put은 업데이트  rest-api가 crud이다.
    suspend fun getBook2( // suspend 일시중지, 코루틴, 하나의 병렬 동시실행, 통신이 다 되어야, ui를 해야하니 블러킹, 비동기 지원
        @Body request : BookDto // 1차적 보안
    )
}

/*
*
*   @Query 어노테이션은 URL 쿼리 파라미터를 나타냅니다.
     "q"는 쿼리 파라미터의 이름이고, query는 해당 파라미터의 값을 전달받는 변수입니다.
     예를 들어, query가 "kotlin"이면, URL은 "/search.json?q=kotlin"이 됩니다.
        * */