package com.desiatko.countries.domain.usecase

import com.desiatko.countries.domain.repository.RequestHandler
import com.desiatko.countries.domain.repository.entity.Country
import com.desiatko.countries.domain.repository.entity.CountryField
import com.desiatko.countries.domain.repository.map
import com.desiatko.countries.domain.repository.model.CountryShortInfo

class AllCountriesUseCase : UseCase<Unit, List<CountryShortInfo>>() {

    override fun createHandler(params: Unit): RequestHandler<List<CountryShortInfo>> {
        return repository.getAllCountries(*SHORT_INFO_FIELDS).map { it.mapNotNull { country -> country.getShortInfo() } }
    }

    private fun Country.getShortInfo(): CountryShortInfo? {
        //by returning null we skip items that does not have required information
        return CountryShortInfo(
            alphaCode = alpha2Code ?: alpha3Code ?: return null,
            name = name ?: return null,
            localName = nativeName ?: return null,
            flag = flag ?: return null,
            population = population ?: return null
        )
    }

    companion object {
        val SHORT_INFO_FIELDS = arrayOf(
            CountryField.ALPHA2CODE,
            CountryField.ALPHA3CODE,
            CountryField.NAME,
            CountryField.NATIVE_NAME,
            CountryField.FLAG,
            CountryField.POPULATION
        )
    }
}