package com.dartharrmi.weathery.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dartharrmi.weathery.base.BaseViewModel
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.ui.livedata.Event
import com.dartharrmi.weathery.usecases.common.IDeleteBookmarkUseCase
import com.dartharrmi.weathery.usecases.detail.IGetBookmarkUseCase
import com.dartharrmi.weathery.usecases.home.IFindCitiesByLocationUseCase
import com.dartharrmi.weathery.usecases.home.IFindCitiesByNameUseCase
import com.dartharrmi.weathery.utils.Logger
import com.dartharrmi.weathery.utils.Logger.LOGE
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val findCitiesByNameUseCase: IFindCitiesByNameUseCase,
                    private val findCitiesByLocationUseCase: IFindCitiesByLocationUseCase,
                    private val getBookmarkUseCase: IGetBookmarkUseCase,
                    private val deleteBookmarkUseCase: IDeleteBookmarkUseCase): BaseViewModel() {

    private val tag = Logger.makeLogTag("HomeViewModel")

    val getBookmarksEvent = MutableLiveData<Event<List<CityWeather>>>()
    val multipleCitiesEvent = MutableLiveData<Event<List<CityWeather>>>()
    val cityFoundEvent = MutableLiveData<Event<CityWeather>>()
    val deleteBookmarkEvent = MutableLiveData<Event<Boolean>>()

    fun getBookmarks() {
        viewModelScope.launch(contextProvider.getMainContext()) {
            showProgress()

            try {
                val result = withContext(contextProvider.getIoContext()) {
                    getBookmarkUseCase.execute()
                }
                getBookmarksEvent.value = Event.success(result)
            } catch (t: Throwable) {
                LOGE(tag, "Error getting the bookmarks", t)
                getBookmarksEvent.value = Event.failure(t)
            } finally {
                hideProgress()
            }
        }
    }

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
}