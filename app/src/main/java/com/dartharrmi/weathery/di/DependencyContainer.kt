package com.dartharrmi.weathery.di

import android.content.Context
import androidx.room.Room
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Local
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Remote
import com.dartharrmi.weathery.repositories.OpenWeatherMapLocalDataSource
import com.dartharrmi.weathery.repositories.OpenWeatherMapRemoteDataSource
import com.dartharrmi.weathery.repositories.WeatherRepository
import com.dartharrmi.weathery.repositories.room.AppDatabase
import com.dartharrmi.weathery.usecases.detail.*
import com.dartharrmi.weathery.usecases.home.FindCitiesByLocationUseCase
import com.dartharrmi.weathery.usecases.home.FindCitiesByNameUseCase
import com.dartharrmi.weathery.webservice.utils.PublicOkHttpClient

/**
 * Container of objects shared across the whole app
 */
object DependencyContainer {

    private lateinit var db: AppDatabase
    private lateinit var localDataSource: Local
    private lateinit var remoteDataSource: Remote
    private lateinit var repository: IOpenWeatherMapDataSource.Repository

    lateinit var findCitiesByNameUseCase: FindCitiesByNameUseCase
        private set

    lateinit var findCitiesByLocationUseCase: FindCitiesByLocationUseCase
        private set

    lateinit var downloadIconUseCase: DownloadIconUseCase
        private set

    lateinit var saveBookmarkUseCase: SaveBookmarkUseCase
        private set

    lateinit var deleteBookmarkUseCase: DeleteBookmarkUseCase
        private set

    lateinit var getBookmarkUseCase: IGetBookmarkUseCase
        private set

    fun initDependencies(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "weathery"
        ).build()

        localDataSource = OpenWeatherMapLocalDataSource(db)
        remoteDataSource = OpenWeatherMapRemoteDataSource(context, PublicOkHttpClient())
        repository = WeatherRepository(localDataSource, remoteDataSource)

        findCitiesByNameUseCase = FindCitiesByNameUseCase(repository)
        findCitiesByLocationUseCase = FindCitiesByLocationUseCase(repository)
        downloadIconUseCase = DownloadIconUseCase(repository)
        saveBookmarkUseCase = SaveBookmarkUseCase(repository)
        deleteBookmarkUseCase = DeleteBookmarkUseCase(repository)
        getBookmarkUseCase = GetBookmarkUseCase(repository)
    }
}