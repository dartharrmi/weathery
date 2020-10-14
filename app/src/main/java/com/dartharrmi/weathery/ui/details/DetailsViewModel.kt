package com.dartharrmi.weathery.ui.details

import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dartharrmi.weathery.base.BaseViewModel
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.ui.livedata.Event
import com.dartharrmi.weathery.usecases.detail.IDeleteBookmarkUseCase
import com.dartharrmi.weathery.usecases.detail.IDownloadIconUseCase
import com.dartharrmi.weathery.usecases.detail.ISaveBookmarkUseCase
import com.dartharrmi.weathery.utils.Logger
import com.dartharrmi.weathery.utils.Logger.LOGE
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val downloadIconUseCase: IDownloadIconUseCase,
                       private val saveBookmarkUseCase: ISaveBookmarkUseCase,
                       private val deleteBookmarkUseCase: IDeleteBookmarkUseCase): BaseViewModel() {

    private val tag = Logger.makeLogTag("DetailsViewModel")

    val deleteBookmarkEvent = MutableLiveData<Event<Boolean>>()
    val saveBookmarkEvent = MutableLiveData<Event<Boolean>>()
    val downloadIconEvent = MutableLiveData<Event<BitmapDrawable>>()

    fun saveBookmark(location: CityWeather) {
        viewModelScope.launch(contextProvider.getMainContext()) {
            showProgress()

            try {
                val result = withContext(contextProvider.getIoContext()) {
                    saveBookmarkUseCase.execute(location)
                }
                saveBookmarkEvent.value = Event.success(result)
            } catch (t: Throwable) {
                LOGE(tag, "Error saving the bookmark", t)
                saveBookmarkEvent.value = Event.failure(t)
            } finally {
                hideProgress()
            }
        }
    }

    fun deleteBookmark(location: CityWeather) {
        viewModelScope.launch(contextProvider.getMainContext()) {
            showProgress()

            try {
                val result = withContext(contextProvider.getIoContext()) {
                    deleteBookmarkUseCase.execute(location)
                }
                deleteBookmarkEvent.value = Event.success(result)
            } catch (t: Throwable) {
                LOGE(tag, "Error saving the bookmark", t)
                deleteBookmarkEvent.value = Event.failure(t)
            } finally {
                hideProgress()
            }
        }
    }

    fun downloadIcon(resources: Resources, url: String) {
        viewModelScope.launch(contextProvider.getMainContext()) {
            showProgress()

            try {
                val result = withContext(contextProvider.getIoContext()) {
                    downloadIconUseCase.execute(resources, url)
                }
                downloadIconEvent.value = Event.success(result)
            } catch (t: Throwable) {
                LOGE(tag, "Error downloading the icon", t)
                downloadIconEvent.value = Event.failure(t)
            } finally {
                hideProgress()
            }
        }
    }
}