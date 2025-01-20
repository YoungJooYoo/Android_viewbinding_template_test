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
// 뷰모델 안에 라이브 데이터 들어간다.
class MainViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    // LiveData를 통해서 데이터를 observing 할 수 있음
    private val _bookInfo = MutableLiveData<BookDto?>()
    val bookInfo: LiveData<BookDto?> = _bookInfo // 뮤터블 데이터를, 다른 클래스 쓰면? 값 꼬인다. read-only

    private val _state = MutableLiveData<String>() //
    val state: LiveData<String> = _state

    // bookRepository.getBook은 네트워크 통신이 존재
    // 비동기로 처리가 되야하기 때문에 suspend 키워드가 존재 -> 코루틴으로 감싸줘야함
    // viewModelScope를 통해서 코루틴 생성
    fun getBook(
        query: String = "",
        language: String = "",
    ) = viewModelScope.launch { // 뷰모델 스코프 코루틴, 스레등 경량화 모습 메인스레드 ui, ios나 백그라운드 워커스레드
        // 메인스레드에 시키면 뻗어, 앱이 버벅 거린다. 하나의 스레드에서, 여러개의 코루틴이 존재한다.
        // 코루틴 하나하나 마다 스레드 역할을 한다. 런치-> 잡  ,async  디퍼지 개체 반환, 블럭 다 하고 결과값 리턴, 2가지존재
        // 2개 차이점 있으니,   런치는 결과값 언제되도 되면 되는 작업이라, 런치로
        // SP에 쿼리 저장
        BookSharedPreference.query = query // 메인스레드에서 작업 쉐어프 프리퍼런스,  나중에~ 데이터 스토어, 자체적 비동기 지원 코루틴 블록더 따로 지정 , 디스패처 존재 (코루틴) 메인디스페처, 디스패처, io디스패처, 스레드 안에 여러개 코루틴 3가지 존재
        // 디스패처 디폴트 : 최대 생성 블럭이 cpu코어수와 똑같다. 이미지작업 통신이 아닌 cpu 사용해야하는 작업
        // 제한적 멀티스레딩 지원 안드로이드
        // runCathing으로 예외처리 - 트라이캐치, 코틀린의 트라이캐치  리턴은 result 객체
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