package com.dartharrmi.weathery.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dartharrmi.weathery.coroutines.CoroutineContextProvider
import com.dartharrmi.weathery.coroutines.ICoroutineContextProvider
import com.dartharrmi.weathery.ui.livedata.Event

abstract class BaseViewModel : ViewModel() {

    val contextProvider: ICoroutineContextProvider = CoroutineContextProvider()

    val isLoadingEvent = MutableLiveData<Event<Boolean>>()

    fun showProgress() {
        isLoadingEvent.value = Event.success(true)
    }

    fun hideProgress() {
        isLoadingEvent.value = Event.success(false)
    }

}