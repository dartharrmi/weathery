package com.dartharrmi.weathery.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomDialogFragment<DB: ViewDataBinding>: BottomSheetDialogFragment() {

    // View Context
    private lateinit var fragmentContext: Context

    // Data binding
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    lateinit var dataBinding: DB

    //region Abstract Base Methods
    abstract fun getLayoutId(): Int

    abstract fun getVariablesToBind(): Map<Int, Any>

    abstract fun initObservers()

    @CallSuper
    open fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        dataBinding.lifecycleOwner = this
        for ((variableId, value) in getVariablesToBind()) {
            dataBinding.setVariable(variableId, value)
        }
        dataBinding.executePendingBindings()
    }
    //endregion

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    final override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        initObservers()
        initView(inflater, container)
        return dataBinding.root
    }
}