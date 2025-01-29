package com.ryusw.android_viewbinding_template.ui.login

import androidx.activity.addCallback
import com.ryusw.android_viewbinding_template.common.base.BaseFragment
import com.ryusw.android_viewbinding_template.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private fun setButton() {
        with(binding){
            btnMain.setOnClickListener {
                navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            }
        }
    }

    override fun initView() {
        setButton()
        handleOnBackPressed()
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