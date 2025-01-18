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