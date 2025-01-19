package com.ryusw.android_viewbinding_template.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.ryusw.android_viewbinding_template.common.dialog.LoadingDialogFragment

// BaseFragment는 모든 프래그먼트에서 공통적으로 사용되는 기능을 추상화한 클래스입니다.
// 이 클래스는 ViewBinding을 사용하여 UI를 효율적으로 관리하며,
// Toast, Snackbar, 네비게이션 등의 공통 동작을 제공합니다.
abstract class BaseFragment<T : ViewBinding>(
    // inflate 함수는 레이아웃을 ViewBinding 객체로 변환하는 함수입니다.
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T,
) : Fragment() {

    // ViewBinding 객체를 저장하는 변수입니다.
    // _binding은 null이 될 수 있으며, 생명주기에 따라 초기화됩니다.
    private var _binding: T? = null

    // binding은 null이 아닌 ViewBinding 객체를 반환합니다.
    // 사용 시 반드시 _binding이 null이 아니어야 하며, 그렇지 않을 경우 예외를 발생시킵니다.
    protected val binding
        get() = requireNotNull(_binding)

    // Toast와 Snackbar 객체를 저장하는 변수입니다.
    private var toast: Toast? = null
    private var snackBar: Snackbar? = null

    // 프래그먼트의 View를 생성하는 메서드입니다.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ViewBinding 객체를 생성하여 _binding 변수에 할당합니다.
        _binding = inflate(inflater, container, false)
        // ViewBinding의 루트 View를 반환하여 화면을 구성합니다.
        return binding.root
    }

    // View가 생성된 후 호출되며, 초기화 작업을 수행합니다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView() // 화면 초기화 작업을 수행하는 추상 메서드.
        initObserving() // 옵저빙 작업을 수행하는 메서드. 필요시 상속받은 클래스에서 재정의 가능합니다.
    }

    // 프래그먼트 초기화 작업을 정의하는 추상 메서드입니다.
    // 모든 상속 클래스에서 구현해야 합니다.
    abstract fun initView()

    // 데이터 관찰(Observing) 작업을 위한 메서드입니다.
    // 기본적으로 빈 메서드로 제공되며, 필요시 상속받아 재정의합니다.
    open fun initObserving() {}

    // 로딩 다이얼로그를 표시합니다.
    protected fun showLoading() {
        if (!LoadingDialogFragment.getInstance().isAdded) {
            LoadingDialogFragment.getInstance().show(childFragmentManager, "loading")
        }
    }

    // 로딩 다이얼로그를 닫습니다.
    protected fun dismissLoading() {
        if (LoadingDialogFragment.getInstance().isAdded) {
            LoadingDialogFragment.getInstance().dismissAllowingStateLoss()
        }
    }

    // Toast 메시지를 표시합니다.
    protected fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toast = Toast.makeText(requireContext(), message, duration).apply {
            show()
        }
    }

    // Snackbar 메시지를 표시합니다.
    protected fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        snackBar = Snackbar.make(binding.root, message, duration).apply {
            show()
        }
    }

    // SafeArgs를 사용하여 네비게이션 동작을 수행합니다.
    protected fun navigate(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    // View가 파괴될 때 호출됩니다.
    // 메모리 누수를 방지하기 위해 _binding을 null로 설정합니다.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 프래그먼트가 완전히 파괴될 때 호출됩니다.
    // Toast와 Snackbar 객체를 null로 설정하여 메모리 누수를 방지합니다.
    override fun onDestroy() {
        super.onDestroy()
        toast = null
        snackBar = null
    }
}
