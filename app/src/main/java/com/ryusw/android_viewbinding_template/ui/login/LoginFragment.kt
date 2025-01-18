package com.ryusw.android_viewbinding_template.ui.login

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
    }
}