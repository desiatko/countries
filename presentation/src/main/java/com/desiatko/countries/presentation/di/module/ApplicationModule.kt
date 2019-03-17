package com.desiatko.countries.presentation.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import com.desiatko.countries.data.NetworkRepository
import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.CoroutineDispatchers
import com.desiatko.countries.presentation.coroutines.CoroutineDispatchersImpl
import com.desiatko.countries.presentation.imageloader.GlideImageLoader
import com.desiatko.countries.presentation.imageloader.ImageLoader
import com.desiatko.countries.presentation.navigation.Navigation
import com.desiatko.countries.presentation.navigation.OneFragmentNavigation
import com.desiatko.countries.presentation.navigation.TwoFragmentNavigation
import com.desiatko.countries.presentation.util.isTablet
import javax.inject.Singleton

@Module
open class ApplicationModule {

    @Provides
    open fun repository(): CountryRepository = NetworkRepository

    @Provides
    open fun coroutines(): CoroutineDispatchers = CoroutineDispatchersImpl

    @Provides
    fun imageLoader(): ImageLoader = GlideImageLoader

    @Singleton
    @Provides
    fun navigation(context: Context): Navigation =
        if (context.isTablet) TwoFragmentNavigation else OneFragmentNavigation
}