package com.dartharrmi.weathery.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dartharrmi.weathery.base.BaseViewModel
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.ui.livedata.Event
import com.dartharrmi.weathery.usecases.IFindCitiesByLocationUseCase
import com.dartharrmi.weathery.usecases.IFindCitiesByNameUseCase
import com.dartharrmi.weathery.utils.Logger
import com.dartharrmi.weathery.utils.Logger.LOGE
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val findCitiesByNameUseCase: IFindCitiesByNameUseCase,
                    private val findCitiesByLocationUseCase: IFindCitiesByLocationUseCase): BaseViewModel() {

    private val tag = Logger.makeLogTag("HomeViewModel")

    val multipleCitiesEvent = MutableLiveData<Event<List<CityWeather>>>()
    val cityFoundEvent = MutableLiveData<Event<CityWeather>>()

    fun findCitiesByName(query: String) {
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
    }

    fun findCitiesByLocation(lat: Double, lng: Double) {
        viewModelScope.launch(contextProvider.getMainContext()) {
            showProgress()

            try {
                val result = withContext(contextProvider.getIoContext()) {
                    findCitiesByLocationUseCase.execute(lat, lng)
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
    }
}