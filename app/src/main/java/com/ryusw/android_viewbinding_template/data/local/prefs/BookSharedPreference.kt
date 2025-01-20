package com.ryusw.android_viewbinding_template.data.local.prefs

import android.content.Context
import com.ryusw.android_viewbinding_template.BookApplication

// key-value 형식의 로컬 저장소
object BookSharedPreference {
    private const val PREFS_NAME = "book_prefs"
    private const val KEY_QUERY = "key_query"

    // Application의 context를 사용
    private val bookPrefs = BookApplication.appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // 프로퍼티 사용
    // set -> 해당 변수를 설정할때 마다 호출
    // get -> 해당 변수를 읽을때 마다 호출
    var query : String
        set(value) = bookPrefs.edit().putString(KEY_QUERY, value).apply()
        get() = bookPrefs.getString(KEY_QUERY, "") ?: "" // 대충 3항 연산자. 널인지 아닌지만 판독
}

// 오브젝트는 싱글턴 = 처음부터 인스턴스로 짠! , 클래스는 클래스