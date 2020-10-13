package com.dartharrmi.weathery.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.DecimalFormat

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun Any.className(): String = this::class.java.simpleName

fun Activity.hideKeyBoard() {
    val inputManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.apply {
        hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
    }
}

fun Double.toStringWithoutScientificNotation(times: Int = 14): String {
    val df = DecimalFormat("###.${"#".repeat(times)}")
    return df.format(this)
}