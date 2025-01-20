package com.ryusw.android_viewbinding_template.ui.splash

import com.ryusw.android_viewbinding_template.common.base.BaseFragment
import com.ryusw.android_viewbinding_template.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate)
{
    override fun initView() {
        setButtonEvent()
    }

    private fun setButtonEvent(){
        with(binding){
            btnMain.setOnClickListener {
                navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
            }
            btnLogin.setOnClickListener {
                navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
    }
}


// 안드로이드 12부터 스플래시 스크린
// 12이상부터, os 자체에서 스플래시 화면 지원
// 스플래시 보여지는 중에 작업 처리,
// 유저가 만들어도 커스텀 가능