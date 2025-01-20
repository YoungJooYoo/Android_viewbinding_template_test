package com.ryusw.android_viewbinding_template

import android.app.Application
import android.content.Context
import com.ryusw.android_viewbinding_template.data.remote.Api
import com.ryusw.android_viewbinding_template.data.remote.service.BookService
import com.ryusw.android_viewbinding_template.data.repository.BookRepository

// 앱 실행 시 가장 먼저 생성되는 Application 클래스.
// Application 클래스를 상속받아, 애플리케이션의 전역 상태 및 객체 초기화를 수행.
// 이 클래스는 AndroidManifest.xml에서 설정되어 앱의 진입점으로 사용됨.
class BookApplication : Application() {

    // 의존성 주입(DI) 프레임워크를 사용하지 않으므로, Repository를 관리.
    // dI -> 디버깅이 힘들고, 가독성 문제, 레포지토리 -> 로컬데이터 or api로 통신
    // lazy를 사용하여 bookRepository 객체를 최초 접근 시 생성. lazy 앱의 초기 실행 속도를 향상시킬 수 있습니다.
    val bookRepository by lazy {
        BookRepository(bookService = Api.createService(BookService::class.java))
    }

    // 애플리케이션 생명주기의 시작 지점.
    // 앱이 생성될 때 호출되며, 전역적으로 사용할 Context를 설정.
    override fun onCreate() {
        super.onCreate()

        appContext = this // 앱 전역에서 사용할 수 있는 Context를 초기화.
    }

    companion object {
        // 애플리케이션의 전역 Context를 관리하는 정적 객체.
        // lateinit을 사용하여 앱 초기화 시 Context를 설정.
        // private set으로 외부에서 Context를 변경하지 못하도록 제한.
        lateinit var appContext: Context
            private set // 설정못하게
    }
}

// 하나의 클래스가 인스턴스
// 프레그먼트 컨테이너를 통해 화면 갈아 끼우는 느낌 액티비티 1개, 리소스 절감 효과, 옛날 단말기에서 속도 향상 체감
// ui 유연하다, 갈아 끼우면 끝, 프레그먼트 복수개 띄위서 ui설정하는 목적