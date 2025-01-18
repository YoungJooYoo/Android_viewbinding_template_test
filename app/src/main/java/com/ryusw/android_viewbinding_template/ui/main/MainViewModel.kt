package com.ryusw.android_viewbinding_template.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ryusw.android_viewbinding_template.data.local.prefs.BookSharedPreference
import com.ryusw.android_viewbinding_template.data.remote.dto.BookDto
import com.ryusw.android_viewbinding_template.data.repository.BookRepository
import kotlinx.coroutines.launch

// ViewModel은 생성된 곳의 LifeCycle을 따라감
// 단, activityViewModel, navGraphViewModel은 각각 앞에 붙은 내용으로 따라감
class MainViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    // LiveData를 통해서 데이터를 observing 할 수 있음
    private val _bookInfo = MutableLiveData<BookDto?>()
    val bookInfo: LiveData<BookDto?> = _bookInfo

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> = _state

    // bookRepository.getBook은 네트워크 통신이 존재
    // 비동기로 처리가 되야하기 때문에 suspend 키워드가 존재 -> 코루틴으로 감싸줘야함
    // viewModelScope를 통해서 코루틴 생성
    fun getBook(
        query: String = "",
        language: String = "",
    ) = viewModelScope.launch {
        // SP에 쿼리 저장
        BookSharedPreference.query = query
        // runCathing으로 예외처리
        kotlin.runCatching {
            bookRepository.getBook(query, language)
        }.onSuccess {
            _bookInfo.value = it
            _state.value = "success"
        }.onFailure {
            _bookInfo.value = null
            _state.value = "fail, message = ${it.message}"
        }
    }
}

// Factory를 통해서 ViewModel을 생성
class MainViewModelFactory (
    private val bookRepository : BookRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(bookRepository) as T
    }
}