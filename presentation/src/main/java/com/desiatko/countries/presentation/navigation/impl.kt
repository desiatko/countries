package com.desiatko.countries.presentation.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.desiatko.countries.presentation.R
import com.desiatko.countries.presentation.view.fragment.CountriesListFragment
import com.desiatko.countries.presentation.view.fragment.CountryDetailsFragment

abstract class CommonFragmentNavigation : Navigation {

    override fun countriesListShown(activity: FragmentActivity): Boolean =
        activity.fragmentShown(R.id.countries_list_container)

    override fun countryDetailsShown(activity: FragmentActivity): Boolean =
        activity.fragmentShown(R.id.country_details_container)

    override fun showCountriesList(activity: FragmentActivity, view: CountriesListFragment) {
        activity.transaction { replace(R.id.countries_list_container, view) }
    }

    protected fun FragmentActivity.fragmentShown(@IdRes containerId: Int) =
        supportFragmentManager.findFragmentById(containerId) != null


    protected fun FragmentActivity.transaction(transaction: FragmentTransaction.() -> Unit) {
        supportFragmentManager!!.beginTransaction().apply(transaction).commit()
    }
}

object OneFragmentNavigation : CommonFragmentNavigation() {
    override fun showCountryDetails(activity: FragmentActivity, view: CountryDetailsFragment) {
        activity.transaction { addToBackStack(null).replace(R.id.countries_list_container, view) }
    }

    override fun showEmptyCountryDetails(activity: FragmentActivity, view: Fragment) {} //no need
}

object TwoFragmentNavigation : CommonFragmentNavigation() {
    override fun showCountryDetails(activity: FragmentActivity, view: CountryDetailsFragment) {
        activity.transaction { replace(R.id.country_details_container, view) }
    }

    override fun showEmptyCountryDetails(activity: FragmentActivity, view: Fragment) {
        activity.transaction { add(R.id.country_details_container, view) }
    }
}