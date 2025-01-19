package com.ryusw.android_viewbinding_template.data.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object RetrofitUtil {

    // File을 멀티파트 형식으로 변경
    fun getMultipartData(key: String, file: File): MultipartBody.Part {
        return file.run {  // run 스코프를 사용하여 코드 블록 실행
            val mediaType = "image/*".toMediaTypeOrNull()  // 미디어 타입 설정 (이미지 모든 타입)
            val requestFile = file.asRequestBody(mediaType)  // 파일을 RequestBody 객체로 변환
            MultipartBody.Part.createFormData(key, file.name, requestFile)  // 멀티파트 데이터 생성
        }
    }

    // File -> RequestBody로 변경
    fun getRequestBody(file: File): RequestBody {
        return file.run {  // run 스코프를 사용하여 코드 블록 실행
            val mediaType = "image/*".toMediaTypeOrNull()  // 미디어 타입 설정 (이미지 모든 타입)
            file.asRequestBody(mediaType)  // 파일을 RequestBody 객체로 변환
        }
    }
}