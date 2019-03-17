package com.desiatko.countries.presentation.contract

import com.desiatko.countries.domain.repository.model.CountryDetailedInfo
import com.desiatko.countries.domain.repository.model.CountryShortInfo
import com.desiatko.countries.presentation.view.BaseView
import com.desiatko.countries.presentation.view.LoadingContentView

//view-presenter pair for screen with country details, presented by CountryDetailedInfo
object CountryDetails {
    interface View : BaseView, LoadingContentView {
        fun showCountryDetails(info: CountryDetailedInfo)
        fun clear()
    }

    interface Presenter {
        fun start(view: View, info: CountryShortInfo)
    }
}