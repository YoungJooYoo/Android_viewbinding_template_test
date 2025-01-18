package com.ryusw.android_viewbinding_template.ui

import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.ryusw.android_viewbinding_template.R
import com.ryusw.android_viewbinding_template.common.base.BaseActivity
import com.ryusw.android_viewbinding_template.databinding.ActivityMainBinding

// Fragment를 보여주는 Acitity
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    // Jetpack Navigation에서 인식하는 Fragment
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.view_container) as NavHostFragment
    }

    override fun initView() {
        super.initView()
        setNavigationListener()
    }

    // 화면 전환 시 이벤트 발생
    private fun setNavigationListener() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.splashFragment -> Toast.makeText(this, "splash", Toast.LENGTH_SHORT).show()
                R.id.mainFragment -> Toast.makeText(this, "main", Toast.LENGTH_SHORT).show()
                R.id.loginFragment -> Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
            }
        }
    }
}