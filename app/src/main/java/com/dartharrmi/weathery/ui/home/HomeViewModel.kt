package com.dartharrmi.weathery.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dartharrmi.weathery.base.BaseViewModel
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.ui.livedata.Event
import com.dartharrmi.weathery.usecases.IFindCitiesUseCase
import com.dartharrmi.weathery.utils.Logger
import com.dartharrmi.weathery.utils.Logger.LOGE
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val findCitiesUseCase: IFindCitiesUseCase) : BaseViewModel() {

    private val tag = Logger.makeLogTag("HomeViewModel")

    val multipleCitiesEvent = MutableLiveData<Event<List<CityWeather>>>()
    val cityFoundEvent = MutableLiveData<Event<CityWeather>>()

    fun findCities(query: String) {
        viewModelScope.launch(contextProvider.getMainContext()) {
            showProgress()

            try {
                val result = withContext(contextProvider.getIoContext()) {
                    findCitiesUseCase.execute(query)
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