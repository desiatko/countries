package com.desiatko.countries.presentation.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.desiatko.countries.presentation.R
import com.desiatko.countries.presentation.navigation.Navigation
import com.desiatko.countries.presentation.util.component
import com.desiatko.countries.presentation.view.fragment.CountriesListFragment
import com.desiatko.countries.presentation.view.fragment.EmptyConutryDetailsFragment
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigation: Navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        component.inject(this)

        if (!navigation.countriesListShown(this)) {
            navigation.showCountriesList(this, CountriesListFragment())
        }
        if (!navigation.countryDetailsShown(this)) {
            navigation.showEmptyCountryDetails(this, EmptyConutryDetailsFragment())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}