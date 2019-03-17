package com.desiatko.countries.presentation.presenter

import androidx.fragment.app.FragmentActivity
import com.desiatko.countries.domain.CoroutineDispatchers
import com.desiatko.countries.domain.repository.Response
import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.repository.model.CountryShortInfo
import com.desiatko.countries.domain.usecase.AllCountriesUseCase
import com.desiatko.countries.presentation.contract.CountriesList
import com.desiatko.countries.presentation.navigation.Navigation
import com.desiatko.countries.presentation.view.fragment.CountryDetailsFragment
import javax.inject.Inject

//implementation of CountriesList.Presenter interface
class CountriesListPresenter @Inject constructor(
    repository: CountryRepository,
    coroutines: CoroutineDispatchers,
    val useCase: AllCountriesUseCase,
    val navigation: Navigation
) : AbstractBasePresenter<List<CountryShortInfo>, CountriesList.View>(repository, coroutines), CountriesList.Presenter {

    override suspend fun executeUseCase(): Response<List<CountryShortInfo>> = useCase.execute(Unit)

    override fun updateView(view: CountriesList.View, data: List<CountryShortInfo>) = view.showCountries(data)

    override fun onCountryClick(activity: FragmentActivity, country: CountryShortInfo) {
        navigation.showCountryDetails(activity, CountryDetailsFragment.create(country))
    }

    override fun onCleared() {
        super.onCleared()
        useCase.cancel()
    }
}
