package com.ryusw.android_viewbinding_template.ui

import android.widget.Toast
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
    // Jetpack Navigation에서 인식하는 Fragment
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.view_container) as NavHostFragment
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