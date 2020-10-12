package com.dartharrmi.weathery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartharrmi.weathery.ui.main.CityListFragment

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CityListFragment.newInstance())
                    .commitNow()
        }
    }
}