package com.ryusw.android_viewbinding_template.data.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object RetrofitUtil {
    // File을 멀티파트 형식으로 변경
    fun getMultipartData(key : String, file : File): MultipartBody.Part {
        return file.run {
            val mediaType = "image/*".toMediaTypeOrNull()
            val requestFile = file.asRequestBody(mediaType)
            MultipartBody.Part.createFormData(key, file.name, requestFile)
        }
    }

    // File -> RequestBody로 변경
    fun getRequestBody(file : File): RequestBody {
        return file.run {
            val mediaType = "image/*".toMediaTypeOrNull()
            file.asRequestBody(mediaType)
        }
    }
}