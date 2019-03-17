package com.desiatko.countries.presentation.di.module

import androidx.fragment.app.Fragment
import com.desiatko.countries.presentation.contract.CountriesList
import com.desiatko.countries.presentation.contract.CountryDetails
import com.desiatko.countries.presentation.di.scopes.CountryDetailsScope
import com.desiatko.countries.presentation.di.scopes.CountriesListScope
import com.desiatko.countries.presentation.presenter.CountriesListPresenter
import com.desiatko.countries.presentation.presenter.CountryDetailsPresenter
import com.desiatko.countries.presentation.presenter.PresentersFactory
import com.desiatko.countries.presentation.util.createPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(@get:Provides val fragment: Fragment) {
    @CountriesListScope
    @Provides
    fun countriesListPresenter(fragment: Fragment, factory: PresentersFactory): CountriesList.Presenter =
        createPresenter<CountriesListPresenter>(fragment, factory)

    @CountryDetailsScope
    @Provides
    fun countryDetailsPresenter(fragment: Fragment, factory: PresentersFactory): CountryDetails.Presenter =
        createPresenter<CountryDetailsPresenter>(fragment, factory)
}