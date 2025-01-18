package com.ryusw.android_viewbinding_template.common.dialog

import com.ryusw.android_viewbinding_template.common.base.BaseDialogFragment
import com.ryusw.android_viewbinding_template.databinding.DialogLoadingBinding

// 통신할 때 마다 보여주는 dialog
class LoadingDialogFragment : BaseDialogFragment<DialogLoadingBinding>(
    DialogLoadingBinding::inflate
) {
    override fun initView() {}

    companion object {
        // 싱글톤 적용
        private var INSTANCE: LoadingDialogFragment? = null

        fun getInstance(): LoadingDialogFragment {
            if (INSTANCE == null) {
                INSTANCE = LoadingDialogFragment()
            }
            return INSTANCE!!
        }
    }
}