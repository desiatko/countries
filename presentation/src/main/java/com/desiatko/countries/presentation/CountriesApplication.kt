package com.desiatko.countries.presentation

import android.app.Application
import com.desiatko.countries.presentation.di.component.ApplicationComponent
import com.desiatko.countries.presentation.di.component.DaggerApplicationComponent

class CountriesApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
            .appContext(this)
            .build()
    }
}