package com.dartharrmi.weathery.webservice.utils

import android.content.Context
import com.dartharrmi.weathery.BuildConfig
import com.dartharrmi.weathery.webservice.WeatheryNetwork
import com.dartharrmi.weathery.webservice.api.IOpenWeatherMapApi

/**
 *
 */
object WebServiceFactory {

    /**
     *
     */
    fun createIOpenWeatherMapApi(
        context: Context,
        publicOkHttpClient: IPublicOkHttpClient
    ): IOpenWeatherMapApi {
        val retrofit = WeatheryNetwork.createRetrofit(
            BuildConfig.OPENWEATHERMAP_API_KEY,
            publicOkHttpClient.create(context)
        )
        return retrofit.create(IOpenWeatherMapApi::class.java)
    }
}