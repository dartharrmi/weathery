package com.dartharrmi.weathery.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Remote
import com.dartharrmi.weathery.utils.toStringWithoutScientificNotation
import com.dartharrmi.weathery.webservice.utils.IPublicOkHttpClient
import com.dartharrmi.weathery.webservice.utils.WebServiceFactory
import java.net.URL

class OpenWeatherMapRemoteDataSource(private val context: Context,
                                     private val httpClient: IPublicOkHttpClient): Remote {

    override fun openWeatherMapApi() = WebServiceFactory.createIOpenWeatherMapApi(context, httpClient)

    override suspend fun findCitiesByName(query: String) = openWeatherMapApi().searchCitiesByName(query)

    override suspend fun findCitiesByLocation(lat: Double, lng: Double) = openWeatherMapApi().searchCitiesByLocation(lat.toStringWithoutScientificNotation(),
                                                                                                                     lng.toStringWithoutScientificNotation())

    override suspend fun downloadIcon(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        URL(url).openStream().use {
            bitmap = BitmapFactory.decodeStream(it)
            it.close()
        }

        return bitmap
    }
}