package com.dartharrmi.weathery.webservice.utils

import android.content.Context
import com.dartharrmi.weathery.webservice.WeatheryOkHttpClient
import okhttp3.OkHttpClient

/**
 *
 */
interface IPublicOkHttpClient {
    fun create(context: Context): OkHttpClient
}

/**
 * Concrete implementation for [IPublicOkHttpClient]
 */
class PublicOkHttpClient: IPublicOkHttpClient {
    override fun create(context: Context) = WeatheryOkHttpClient.createPublicOkHttpClient(context)
}