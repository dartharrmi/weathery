package com.dartharrmi.weathery.usecases.detail

import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import com.dartharrmi.weathery.domain.exceptions.DownloadIconFailedException
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Repository

interface IDownloadIconUseCase {

    suspend fun execute(resources: Resources, url: String): BitmapDrawable
}

class DownloadIconUseCase(private val repository: Repository): IDownloadIconUseCase {

    override suspend fun execute(resources: Resources, url: String): BitmapDrawable {
        val result = repository.downloadIcon(url)

        result?.let {
            return BitmapDrawable(resources, it)
        } ?: throw DownloadIconFailedException("Error downloading the icon")
    }
}