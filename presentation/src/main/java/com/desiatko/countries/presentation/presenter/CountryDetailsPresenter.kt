package com.desiatko.countries.presentation.presenter

import com.desiatko.countries.domain.CoroutineDispatchers
import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.repository.model.CountryDetailedInfo
import com.desiatko.countries.domain.repository.model.CountryShortInfo
import com.desiatko.countries.domain.usecase.GetCountryDetailsUseCase
import com.desiatko.countries.presentation.contract.CountryDetails
import javax.inject.Inject

class CountryDetailsPresenter @Inject constructor(
    repository: CountryRepository,
    coroutines: CoroutineDispatchers,
    val useCase: GetCountryDetailsUseCase
) : AbstractBasePresenter<CountryDetailedInfo, CountryDetails.View>(repository, coroutines), CountryDetails.Presenter {

    lateinit var countryInfo: CountryShortInfo

    override suspend fun executeUseCase() =  useCase.execute(countryInfo)

    override fun updateView(view: CountryDetails.View, data: CountryDetailedInfo) = view.showCountryDetails(data)

    override fun start(view: CountryDetails.View, info: CountryShortInfo) {
        countryInfo = info
        start(view)
    }

    override fun onCleared() {
        super.onCleared()
        useCase.cancel()
        view.clear()
    }
}