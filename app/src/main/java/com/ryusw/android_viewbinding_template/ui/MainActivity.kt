package com.ryusw.android_viewbinding_template.ui

import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.NavHostFragment
import com.ryusw.android_viewbinding_template.R
import com.ryusw.android_viewbinding_template.common.base.BaseActivity
import com.ryusw.android_viewbinding_template.databinding.ActivityMainBinding


//  **MainActivity** 는 앱의 메인 액티비티
//  화면 레이아웃을 위해 `ActivityMainBinding` 을 사용
// Fragment를 보여주는 Acitity
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate // inflate는 안드로이드에서 XML 레이아웃 파일에 정의된 뷰(View)들을 메모리에 객체 형태로 로드하는 과정을 의미합니다.
) {
    // Jetpack Navigation에서 인식하는 Fragment, 성능성도 있으나,
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.view_container) as NavHostFragment
        // 서포트 프레그먼트 매니저, 현재 액티비티에서 프레그먼터 다루는 매니저, 액티비티 현재는가져온다.
        // 차일드 프레그먼트 매니저 자식 프래그먼트 매니저,
        // 패런트 매니저, 부모 프개르먼트 매니저,
        // lateinit, var서 사용, val as lazy -> null 세이프티 보장
        // as is 캐스팅 2가지, 블럭안에서만 is (블럭안에서만)
    }


    override fun initView() {
        super.initView()
        setNavigationListener()
    }

    //   - 목적: 프래그먼트 전환 시 이벤트 처리
    //   - navHostFragment.navController: 현재 네비게이션 컨트롤러를 가져옵니다.
    //   - addOnDestinationChangedListener: 목적지 변경 리스너를 등록합니다.
    //     - 매개변수:
    //       - controller:  NavHostController
    //       - destination: 이동한 목적지(Fragment) 정보를 가지고 있는 NavDestination 객체
    //       - arguments:  Bundle
    //   - when (destination.id): 목적지(Fragment)의 id 에 따라 분기합니다.
    //     - R.id.splashFragment, R.id.mainFragment, R.id.loginFragment: 각 id 에 해당하는 목적지일 경우 토스트 메시지를 출력합니다.
    // 화면 전환 시 이벤트 발생
    private fun setNavigationListener() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> Toast.makeText(this, "splash", Toast.LENGTH_SHORT).show()
                R.id.mainFragment -> Toast.makeText(this, "main", Toast.LENGTH_SHORT).show()
                R.id.loginFragment -> Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
            }
        }
    }
}