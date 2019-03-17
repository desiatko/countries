package com.desiatko.countries.presentation.di.module

import androidx.lifecycle.ViewModel
import com.desiatko.countries.presentation.di.PresenterKey
import com.desiatko.countries.presentation.presenter.CountriesListPresenter
import com.desiatko.countries.presentation.presenter.CountryDetailsPresenter
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentersBindingModule {

    @Binds
    @IntoMap
    @PresenterKey(CountriesListPresenter::class)
    abstract fun coutriesListPresenter(presenter: CountriesListPresenter): ViewModel

    @Binds
    @IntoMap
    @PresenterKey(CountryDetailsPresenter::class)
    abstract fun coutriyDetailsPresenter(presenter: CountryDetailsPresenter): ViewModel

}