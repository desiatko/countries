package com.desiatko.countries.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.desiatko.countries.presentation.view.fragment.CountriesListFragment
import com.desiatko.countries.presentation.view.fragment.CountryDetailsFragment

//Navigation strategy interface
//It differs for phone/tablet
interface Navigation {
    fun showCountriesList(activity: FragmentActivity, view: CountriesListFragment)
    fun countriesListShown(activity: FragmentActivity): Boolean
    fun showEmptyCountryDetails(activity: FragmentActivity, view: Fragment)
    fun showCountryDetails(activity: FragmentActivity, view: CountryDetailsFragment)
    fun countryDetailsShown(activity: FragmentActivity): Boolean
}