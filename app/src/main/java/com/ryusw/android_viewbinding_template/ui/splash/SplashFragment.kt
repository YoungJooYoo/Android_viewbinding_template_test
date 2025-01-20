package com.ryusw.android_viewbinding_template.ui.splash

import androidx.activity.addCallback
import com.ryusw.android_viewbinding_template.common.base.BaseFragment
import com.ryusw.android_viewbinding_template.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate)
{
    override fun initView() {
        setButtonEvent()
        handleOnBackPressed()
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

    private fun handleOnBackPressed() {
        // 뒤로 가기 버튼이 눌렸을 때 실행될 코드
        // 프레그먼트 마다로 가정하고 버튼 시나리오 생각, 최대한 캡슐화로 진행, 종속성 안가지게
        // 프레그먼트 마다 커스텀
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }
    }
}


// 안드로이드 12부터 스플래시 스크린
// 12이상부터, os 자체에서 스플래시 화면 지원
// 스플래시 보여지는 중에 작업 처리,
// 유저가 만들어도 커스텀 가능