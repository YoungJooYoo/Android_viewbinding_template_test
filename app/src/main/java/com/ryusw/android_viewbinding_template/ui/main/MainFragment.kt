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
    // 통신로직이 들어가는 프레그먼트,  뷰모델 : 데이터 홀더 , 생명주기를 따라감(프레그먼트의)
    // 메인프레그먼트 죽으면, 메인 뷰모델 같이 죽는다.
    // viewModel의 생성자에 변수가 존재 시, factory 사용
    private val viewModel : MainViewModel by viewModels {
        MainViewModelFactory((requireActivity().application as BookApplication).bookRepository)
    } // 팩토리 패턴 사용 :: 액티비티 정보 require로 가져와서 애플리케이션 정보, 북 애플리케이션으로 캐스팅 후,
    // 그 값인 북 레파지토리를 가져온다.  프레그먼트를 바로 못가져와서, 이렇게

    override fun initView() {
        // 키패드 on이 되면 editText 영역 까지 확인
        activity?.window?.run {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            // 소프트 인풋, 키패드 올라오면 같이 정의해서 맞춰주게, ui 때문에
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
            // 위 코드는 값이 변하는 순간 메소드 실행, 값이 변하는 것을 옵저빙을 하겠다.
            // 뷰 라이프 사이클에 넣은게, 언제까지 옵저빙 하겠는가??.
            // 데이터 옵저빙 이유?? 서버 통신, 만약 한화면에 또 데이터를 바꿔야하는 상황인데 통신 api 사용후, 콜백 사용하지만,
            // 안드로이드 안좋은 이유가 콜백 안에 콜백안에 들어가는 구조가 된다. 막기 위해서 옵저빙 패턴을 한다.
            // 콜백 받을 필요없이, 옵저빙 후 데이터 변경 하기 위해서 == 콜백지옥을 해결한다.
        }
        viewModel.state.observe(viewLifecycleOwner) {
            binding.tvState.text = it
        }
    }

    private fun initState() {
        // 앱 실행 시, 로컬 저장소에서 데이터 가져오기
        binding.editBook.setText(BookSharedPreference.query) // 키-밸류는 싱근턴으로 구현됨, 단순데이터
        // 룸 프레임워크는 db를, 테이블 형식 클래스 관련으로, 복잡한 데이터 타입!
    }

    private fun initButton() {  // 서치버튼에 사용
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

// 메인엑티비티는 컨테이너를 위한 것, 실제적 ui단에서 유저가 느끼는 것은 메인 프레그먼트