package com.ryusw.android_viewbinding_template.ui.signup

import com.ryusw.android_viewbinding_template.common.base.BaseDialogFragment
import com.ryusw.android_viewbinding_template.common.dialog.LoadingDialogFragment
import com.ryusw.android_viewbinding_template.databinding.DialogLoadingBinding

// 통신할 때 마다 보여주는 dialog
// 로딩 중임을 나타내는 다이얼로그 프래그먼트
class TermsFragment : BaseDialogFragment<DialogLoadingBinding>(
    DialogLoadingBinding::inflate // 뷰 바인딩을 위한 inflate 메서드 전달
) {
    // initView() 메서드 오버라이드
    // 다이얼로그 초기화 작업을 수행하는 메서드. 여기서는 특별한 초기화 작업이 없으므로 비어 있음.
    override fun initView() {}

    companion object {
        // 싱글톤 적용
        // LoadingDialogFragment의 인스턴스를 저장하는 변수
        private var INSTANCE: LoadingDialogFragment? = null

        // LoadingDialogFragment의 인스턴스를 반환하는 메서드
        fun getInstance(): LoadingDialogFragment {
            // INSTANCE가 null이면 새로운 LoadingDialogFragment 인스턴스를 생성
            if (INSTANCE == null) {
                INSTANCE = LoadingDialogFragment()
            }
            // INSTANCE가 null이 아님이 보장되므로 !! (non-null assertion operator)를 사용하여 반환
            return INSTANCE!!
        }
    }
}