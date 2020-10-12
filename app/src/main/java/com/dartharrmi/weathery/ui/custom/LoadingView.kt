package com.dartharrmi.weathery.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.dartharrmi.weathery.R
import com.dartharrmi.weathery.ui.livedata.Event
import com.dartharrmi.weathery.ui.livedata.Status

class LoadingView : FrameLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() = inflate(context, R.layout.loading_view, this)

    fun onLoadingResponse(event: Event<Boolean>, window: Window?) {
        when (event.status) {
            Status.SUCCESS -> onLoadingResponseSuccess(event, window)
            Status.FAILURE -> Unit
        }
    }

    private fun onLoadingResponseSuccess(event: Event<Boolean>, window: Window?) {
        if (event.data == true)
            showLoading(window)
        else
            hideLoading()
    }

    fun showLoading(window: Window?) {
        if (parent == null) {
            window?.addContentView(
                this,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    fun hideLoading() {
        parent?.let { (it as ViewGroup).removeView(this) }
    }

}