package com.ryusw.android_viewbinding_template.common.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

// BaseDialogFragment는 커스텀 다이얼로그를 만들기 위한 추상 클래스입니다.
// 이 클래스는 공통적인 다이얼로그 설정 및 ViewBinding 처리를 제공합니다.
// T는 ViewBinding의 타입으로, 다이얼로그 레이아웃에 사용됩니다.
abstract class BaseDialogFragment<T : ViewBinding>(
    // inflate 함수는 레이아웃을 ViewBinding 객체로 변환하는 함수입니다.
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T,
) : DialogFragment() {

    // ViewBinding 객체를 저장하는 변수.
    // _binding은 null이 될 수 있으며, 생명주기에 따라 초기화됩니다.
    private var _binding: T? = null

    // binding은 null이 아닌 ViewBinding 객체를 반환합니다.
    // 사용 시 반드시 _binding이 null이 아니어야 하며, 그렇지 않을 경우 예외를 발생시킵니다.
    protected val binding
        get() = requireNotNull(_binding)

    // onCreate는 다이얼로그가 생성될 때 호출됩니다.
    // 기본적으로 다이얼로그는 취소할 수 없도록 설정합니다.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false // 다이얼로그 외부 터치로 취소되지 않도록 설정.
    }

    // 다이얼로그의 View를 생성하고 초기화합니다.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ViewBinding 객체를 생성하여 _binding 변수에 할당합니다.
        _binding = inflate(inflater, container, false)

        // 다이얼로그의 Window 설정을 수정합니다.
        dialog?.window?.run {
            // 다이얼로그 배경을 투명하게 설정.
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // 다이얼로그의 기본 제목을 비활성화.
            requestFeature(Window.FEATURE_NO_TITLE)
        }

        // 생성된 ViewBinding 객체의 루트 View를 반환합니다.
        return binding.root
    }

    // 다이얼로그 View가 생성된 후 호출됩니다.
    // initView 메서드를 호출하여 구체적인 초기화 작업을 수행합니다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView() // 추상 메서드 호출.
    }

    // 다이얼로그를 사용하는 클래스에서 반드시 구현해야 하는 초기화 메서드.
    // 다이얼로그의 UI나 동작을 정의합니다.
    abstract fun initView()

    // View가 파괴될 때 호출됩니다.
    // 메모리 누수를 방지하기 위해 _binding 변수를 null로 설정합니다.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // ViewBinding 객체를 해제하여 메모리 누수 방지.
    }
}
