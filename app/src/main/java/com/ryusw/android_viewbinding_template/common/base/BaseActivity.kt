package com.ryusw.android_viewbinding_template.common.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding

/**
 * ViewBinding을 사용하는 Activity의 기본 클래스입니다.
 *
 * @param T ViewBinding 클래스
 * @property inflate ViewBinding 객체를 생성하는 함수
 */
open class BaseActivity<T : ViewBinding>(
    private val inflate: (LayoutInflater) -> T
) : AppCompatActivity() {

    // ViewBinding 객체를 저장하는 변수입니다. null 허용.
    private var _binding: T? = null // 메모리 해제 위해서 null 받을 수 있게 선언

    // ViewBinding 객체를 반환하는 프로퍼티입니다. null이면 예외를 발생시킵니다.
    protected val binding
        get() = requireNotNull(_binding) // getter 설정, 명시적으로 null 아님을,

    /**
     * Activity가 생성될 때 호출됩니다.
     * ViewBinding 객체를 생성하고, setContentView()를 호출하여 화면에 표시합니다.
     * Android 13 이상에서는 setEdgePadding()을 호출하여 화면 가장자리에 패딩을 추가합니다.
     *
     * @param savedInstanceState Activity의 이전 상태를 저장하는 Bundle 객체
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater) // ViewBinding 객체 생성
        setContentView(binding.root) // ViewBinding 객체를 사용하여 화면에 표시

        // Android 15 이상에서만 실행
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            setEdgePadding()
        }
    }

    /**
     * 화면 가장자리에 패딩을 추가합니다.
     * 시스템 UI (상태 표시줄, 내비게이션 바) 영역을 고려하여 컨텐츠가 가려지지 않도록 합니다.
     */
    private fun setEdgePadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // 시스템 UI 영역만큼 패딩을 추가합니다.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * 뷰를 초기화하는 함수입니다.
     * BaseActivity를 상속받는 Activity에서 필요에 따라 오버라이드하여 사용합니다.
     */
    open fun initView() {} // open 구현도 아니어도, abstarct 겁데기 반드시 구현
    //액티비티마다 뷰 초기화 할건지 아닌건지에 따라, open abstarct

    /**
     * Activity가 소멸될 때 호출됩니다.
     * ViewBinding 객체를 null로 설정하여 메모리 누수를 방지합니다.
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}