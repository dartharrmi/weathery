package com.dartharrmi.weathery.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.dartharrmi.weathery.di.DependencyContainer

/**
 * Base [Application] for the current app, initializes the dependency injection.
 */
class BaseApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        DependencyContainer.initDependencies(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}