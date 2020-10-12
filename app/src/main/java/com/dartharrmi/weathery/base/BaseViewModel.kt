package com.dartharrmi.weathery.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dartharrmi.weathery.ui.livedata.Event

abstract class BaseViewModel : ViewModel() {

    // val contextProvider by inject<ICoroutineContextProvider>()

    val isLoadingEvent = MutableLiveData<Event<Boolean>>()

    fun showProgress() {
        isLoadingEvent.value = Event.success(true)
    }

    fun hideProgress() {
        isLoadingEvent.value = Event.success(false)
    }

}