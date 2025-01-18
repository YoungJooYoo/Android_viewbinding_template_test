package com.ryusw.android_viewbinding_template.data.remote.dto

import com.google.gson.annotations.SerializedName

// 서버 통신을 통해 가져오는 DTO
data class BookDto (
    @SerializedName("numFound") val numFound : Int?,
    @SerializedName("start") val start : Int?,
    @SerializedName("numFoundExact") val numFoundExact : Boolean?
)