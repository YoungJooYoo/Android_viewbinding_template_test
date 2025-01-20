package com.ryusw.android_viewbinding_template.ui.main

import android.view.WindowManager
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.ryusw.android_viewbinding_template.BookApplication
import com.ryusw.android_viewbinding_template.common.base.BaseFragment
import com.ryusw.android_viewbinding_template.data.local.prefs.BookSharedPreference
import com.ryusw.android_viewbinding_template.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(
    FragmentMainBinding::inflate
){
    // viewModel의 생성자에 변수가 존재 시, factory 사용
    private val viewModel : MainViewModel by viewModels {
        MainViewModelFactory((requireActivity().application as BookApplication).bookRepository)
    }

    override fun initView() {
        // 키패드 on이 되면 editText 영역 까지 확인
        activity?.window?.run {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
        initButton()
        initState()
        handleOnBackPressed()
    }

    override fun initObserving() {
        super.initObserving()
        // 변수가 변경되면 실행되도록 구현
        viewModel.bookInfo.observe(viewLifecycleOwner) {
            dismissLoading()
            binding.tvData.text = "numFound = ${it?.numFound}\nnumFoundExact = ${it?.numFoundExact}"
        }
        viewModel.state.observe(viewLifecycleOwner) {
            binding.tvState.text = it
        }
    }

    private fun initState() {
        // 앱 실행 시, 로컬 저장소에서 데이터 가져오기
        binding.editBook.setText(BookSharedPreference.query)
    }

    private fun initButton() {
        binding.btnSearch.setOnClickListener {
            showLoading()
            // 데이터 요청
            viewModel.getBook(
                query = binding.editBook.text.toString(),
                language = "en"
            )
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