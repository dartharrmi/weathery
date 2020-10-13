package com.dartharrmi.weathery.ui.details

import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dartharrmi.weathery.base.BaseViewModel
import com.dartharrmi.weathery.ui.livedata.Event
import com.dartharrmi.weathery.usecases.IDownloadIconUseCase
import com.dartharrmi.weathery.utils.Logger
import com.dartharrmi.weathery.utils.Logger.LOGE
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val downloadIconUseCase: IDownloadIconUseCase): BaseViewModel() {

    private val tag = Logger.makeLogTag("DetailsViewModel")

    // val multipleCitiesEvent = MutableLiveData<Event<List<CityWeather>>>()
    val downloadIconEvent = MutableLiveData<Event<BitmapDrawable>>()

    /*fun findCitiesByName(query: String) {
        viewModelScope.launch(contextProvider.getMainContext()) {
            showProgress()

            try {
                val result = withContext(contextProvider.getIoContext()) {
                    findCitiesByNameUseCase.execute(query)
                }

                if (result.size == 1) {
                    cityFoundEvent.value = Event.success(result[0])
                } else {
                    multipleCitiesEvent.value = Event.success(result)
                }
            } catch (t: Throwable) {
                LOGE(tag, "Error parsing the cities", t)
                cityFoundEvent.value = Event.failure(t)
            } finally {
                hideProgress()
            }
        }
    }*/

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