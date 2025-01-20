package com.ryusw.android_viewbinding_template.data.remote.dto

import com.google.gson.annotations.SerializedName

// 서버 통신을 통해 가져오는 DTO : Data Transfer Object , 데이터 받는 형식
// DTO(Data Transfer Object)는 데이터 전송 객체로, 네트워크 또는 계층 간 데이터 전송을 위해 사용되는 객체입니다.
data class BookDto(
    @SerializedName("numFound") val numFound: Int?, // SerializedName -> 서버는 numfound로 인식하게 해줌 카멜인지 스네이펴시인지 상관없이
    @SerializedName("start") val start: Int?,
    @SerializedName("numFoundExact") val numFoundExact: Boolean?
)