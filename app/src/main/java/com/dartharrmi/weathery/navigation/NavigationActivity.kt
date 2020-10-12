package com.dartharrmi.weathery.navigation

import com.dartharrmi.weathery.R
import com.dartharrmi.weathery.base.BaseActivity
import com.dartharrmi.weathery.databinding.ActivityNavigationBinding

/**
 * Activity for the Navigation Component.
 */
class NavigationActivity : BaseActivity<ActivityNavigationBinding>() {
    override fun getLayoutId() = R.layout.activity_navigation

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() = Unit
}