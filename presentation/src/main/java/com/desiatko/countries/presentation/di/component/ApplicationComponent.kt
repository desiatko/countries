package com.desiatko.countries.presentation.di.component

import android.content.Context
import com.desiatko.countries.presentation.di.module.ApplicationModule
import com.desiatko.countries.presentation.di.module.FragmentModule
import com.desiatko.countries.presentation.view.activity.MainActivity
import dagger.Component
import dagger.BindsInstance
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun countiesListComponent(module: FragmentModule): CountriesListComponent
    fun countryDetailsComponent(module: FragmentModule): CountryDetailsComponent

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun appContext(context: Context): Builder

        fun appModule(module: ApplicationModule): Builder
    }
}