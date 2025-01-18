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

abstract class BaseFragment<T : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T,
) : Fragment() {

    private var _binding: T? = null
    protected val binding
        get() = requireNotNull(_binding)

    private var toast: Toast? = null
    private var snackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserving()
    }

    abstract fun initView()
    open fun initObserving() {}

    protected fun showLoading() {
        if(!LoadingDialogFragment.getInstance().isAdded){
            LoadingDialogFragment.getInstance().show(childFragmentManager, "loading")
        }
    }
    protected fun dismissLoading() {
        if(LoadingDialogFragment.getInstance().isAdded) {
            LoadingDialogFragment.getInstance().dismissAllowingStateLoss()
        }
    }
    protected fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toast = Toast.makeText(requireContext(), message, duration).apply {
            show()
        }
    }

    protected fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        snackBar = Snackbar.make(binding.root, message, duration).apply {
            show()
        }
    }

    protected fun navigate(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        toast = null
        snackBar = null
    }
}