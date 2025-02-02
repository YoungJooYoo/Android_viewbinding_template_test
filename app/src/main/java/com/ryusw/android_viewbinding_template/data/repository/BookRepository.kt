package com.ryusw.android_viewbinding_template.data.repository

import com.ryusw.android_viewbinding_template.data.remote.dto.BookDto
import com.ryusw.android_viewbinding_template.data.remote.service.BookService


// 로컬 및 통신을 통해 가져오는 데이터의 저장소  - 로컬이던, 리모트던 일단은 여기에 다 받는 곳
// Sp는 단순 데이터이기때문에 object로 구현
class BookRepository(
    private val bookService: BookService // 인스턴스화
) {
    // 비동기 통신이기 때문에 suspend
    suspend fun getBook(
        query: String,
        language: String,
    ): BookDto {
        return bookService.getBook(query, language) // 통신코드가 실행됨
    }
}