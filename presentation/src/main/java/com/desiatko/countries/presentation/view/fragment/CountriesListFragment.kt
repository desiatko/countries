package com.desiatko.countries.presentation.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiatko.countries.domain.repository.model.CountryShortInfo
import com.desiatko.countries.presentation.R
import com.desiatko.countries.presentation.contract.CountriesList
import com.desiatko.countries.presentation.di.module.FragmentModule
import com.desiatko.countries.presentation.imageloader.ImageLoader
import com.desiatko.countries.presentation.util.component
import com.desiatko.countries.presentation.view.adapter.CountriesListAdapter
import javax.inject.Inject


class CountriesListFragment : LoadingContentFragment(), CountriesList.View {

    override val contentLayoutResource: Int = R.layout.fragment_countries_list
    lateinit var listView: RecyclerView
    @Inject
    lateinit var presenter: CountriesList.Presenter
    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component
            .countiesListComponent(FragmentModule(this))
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.content)
        listView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        presenter.start(this)
    }

    override fun showCountries(countries: List<CountryShortInfo>) {
        listView.adapter = CountriesListAdapter(resources, countries, imageLoader) {
            presenter.onCountryClick(activity!!, it)
        }
    }
}