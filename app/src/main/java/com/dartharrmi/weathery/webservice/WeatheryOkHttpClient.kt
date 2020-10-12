package com.dartharrmi.weathery.webservice

import android.content.Context
import com.dartharrmi.weathery.BuildConfig
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
 * Object to create OKHttpClient and customized headers in http requests.
 */
object WeatheryOkHttpClient {

    const val PARAM_API_KEY = "appid"

    private var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
    private var timeOutInSeconds: Long = 60

    /**
     * Set log level of Http requests. Default value is [HttpLoggingInterceptor.Level.BODY]
     */
    @JvmStatic
    fun setLogLevel(logLevel: HttpLoggingInterceptor.Level) {
        this.logLevel = logLevel
    }

    /**
     * Set read and write time out http requests in SECONDS. Default value is 60 seconds
     */
    @JvmStatic
    fun setTimeOut(timeInSeconds: Long) {
        this.timeOutInSeconds = timeInSeconds
    }

    /**
     * Creates a public OkHttpClient to consume web services
     *
     * @param [context]     Android SDK runtime context.
     * @param [headers]     Optional customized headers in Map<String,String> type.
     */
    @JvmStatic
    @JvmOverloads
    fun createPublicOkHttpClient(
        context: Context,
        headers: Map<String, String> = mapOf()
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        setUpHeadersAndTimeOut(builder)
        return builder.build()
    }

    private fun setUpHeadersAndTimeOut(
        builder: OkHttpClient.Builder,
        currentHeaders: Map<String, String> = emptyMap()
    ) {
        builder.apply {
            connectTimeout(timeOutInSeconds, TimeUnit.SECONDS)
            readTimeout(timeOutInSeconds, TimeUnit.SECONDS)
            writeTimeout(timeOutInSeconds, TimeUnit.SECONDS)
            addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                for (entry in currentHeaders.entries) {
                    requestBuilder.addHeader(entry.key, entry.value)
                }
                requestBuilder.method(original.method(), original.body())
                chain.proceed(requestBuilder.build())
            }
            addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalHttpUrl: HttpUrl = originalRequest.url()

                val url = originalHttpUrl.newBuilder().also {
                    it.addQueryParameter(PARAM_API_KEY, BuildConfig.OPENWEATHERMAP_API_KEY)
                }.build()

                // Request customization: add request headers
                // Request customization: add request headers
                val requestBuilder: Request.Builder = originalRequest.newBuilder()
                    .url(url)

                val request: Request = requestBuilder.build()
                chain.proceed(request)

            }
            addInterceptor(HttpLoggingInterceptor().apply {
                this.level = logLevel
            })
        }
    }
}