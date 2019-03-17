package com.desiatko.countries.presentation.contract

import androidx.fragment.app.FragmentActivity
import com.desiatko.countries.domain.repository.model.CountryShortInfo
import com.desiatko.countries.presentation.presenter.BasePresenter
import com.desiatko.countries.presentation.view.BaseView
import com.desiatko.countries.presentation.view.LoadingContentView

//view-presenter pair for screen with list of countries, presented by CountryShortInfo
object CountriesList {
    interface View : BaseView, LoadingContentView {
        fun showCountries(countries: List<CountryShortInfo>)
    }

    interface Presenter : BasePresenter<View> {
        fun onCountryClick(activity: FragmentActivity, country: CountryShortInfo)
    }
}