package com.ryusw.android_viewbinding_template.data.remote.interceptor

import com.ryusw.android_viewbinding_template.data.local.prefs.BookSharedPreference
import com.ryusw.android_viewbinding_template.data.remote.Api
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

// 소셜로그인 이후 Header에 JWT를 넣어달라고 함
// 통신 할 때 마다 이를 자동으로 넣어주는 코드
// 현재는 사용하지 않음
class AddTokenInterceptor(
    private val testPrefs : BookSharedPreference
): Interceptor {

    // intercept 메서드 오버라이드
    // 이 메서드는 네트워크 요청이 발생할 때마다 호출됩니다.
    // 루티서버에서, 누가 요청하면 우리가 인터셉터로 가로채셔 부가적 처리하고 비즈니스로직 넘김
    override fun intercept(chain: Interceptor.Chain): Response {

        // 현재 요청(Request) 객체를 가져옵니다.
        val request = chain.request()

        // 요청을 수정하기 위한 Builder 객체를 생성합니다.
        val builder = request.newBuilder()

        // 인메모리 데이터로 가지고 있음
        // Api.ACCESS_TOKEN이 비어있는 경우(초기 상태 또는 토큰 갱신이 필요한 경우)에만 로컬 저장소에서 토큰을 가져옵니다.
        // ifEmpty 함수는 문자열이 비어있거나 null인 경우 람다식을 실행합니다.
        // 쉐어드 프리퍼런스에 저장된 토큰인데, 모든 스레드를 일시정지 넌블러킹, 통신마다 정지, 반복하면 오래 걸리니,
        // 첫통신만 가져오고, 성공하면, 인메모리 형식으로 가진다. api개체안에 가지고 있다.
        Api.ACCESS_TOKEN.ifEmpty {
            // runBlocking을 사용하여 코루틴을 동기적으로 실행합니다.
            // 로컬 저장소 접근은 I/O 작업이므로 코루틴에서 실행해야 합니다.
            runBlocking {
                // testPrefs.query를 통해 로컬 저장소(SharedPreference)에서 토큰을 가져와 Api.ACCESS_TOKEN에 저장합니다.
                Api.ACCESS_TOKEN = testPrefs.query
            }
        }

        // Authorization 헤더에 Bearer 토큰을 추가합니다.
        // "Bearer " 뒤에 실제 토큰 값을 붙여서 헤더 값으로 설정합니다.
        // 인터셉터 작업, 가로채고 추가할거 추가
        builder.addHeader("Authorization", "Bearer ${Api.ACCESS_TOKEN}")

        // 수정된 요청(Request)을 사용하여 체인을 진행하고 응답(Response)을 받습니다.
        // 변조후 return -> 서버로 보냄
        return chain.proceed(builder.build())
    }
}



