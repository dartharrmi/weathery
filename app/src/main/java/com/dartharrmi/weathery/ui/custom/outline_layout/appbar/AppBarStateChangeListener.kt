package com.dartharrmi.weathery.ui.custom.outline_layout.appbar

import com.dartharrmi.weathery.ui.custom.outline_layout.appbar.AppBarState.COLLAPSED
import com.dartharrmi.weathery.ui.custom.outline_layout.appbar.AppBarState.EXPANDED
import com.dartharrmi.weathery.ui.custom.outline_layout.appbar.AppBarState.IDLE
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

/**
 * Possible statuses for the [AppBarLayout]
 */
enum class AppBarState {
    EXPANDED,
    COLLAPSED,
    IDLE
}

/**
 * Notifies to subscriber updates on [AppBarLayout] states
 */
class AppBarStateChangeListener(
        private val onStateChanged: ((AppBarLayout, AppBarState) -> Unit)? = null): AppBarLayout.OnOffsetChangedListener {

    private var currentState = IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val prevState = currentState
        currentState = when {
            verticalOffset == 0 -> {
                EXPANDED
            }

            abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                COLLAPSED
            }

            else -> {
                IDLE
            }
        }

        if (currentState !== prevState) {
            onStateChanged(appBarLayout, currentState)
        }
    }

    private fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarState) {
        onStateChanged?.invoke(appBarLayout, state)
    }
}