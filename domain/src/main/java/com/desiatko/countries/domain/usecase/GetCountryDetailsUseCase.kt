package com.desiatko.countries.domain.usecase

import com.desiatko.countries.domain.repository.RequestHandler
import com.desiatko.countries.domain.repository.entity.Country
import com.desiatko.countries.domain.repository.entity.CountryField
import com.desiatko.countries.domain.repository.map
import com.desiatko.countries.domain.repository.model.CountryDetailedInfo
import com.desiatko.countries.domain.repository.model.CountryShortInfo

class GetCountryDetailsUseCase : UseCase<CountryShortInfo, CountryDetailedInfo>() {

    override fun createHandler(params: CountryShortInfo): RequestHandler<CountryDetailedInfo> {
        val code = params.alphaCode
        return repository.getCountryDescription(code, *DETAILED_INFO_FIELDS)
            .map { it.map(params) }
    }

    private fun Country.map(info: CountryShortInfo): CountryDetailedInfo {
        return CountryDetailedInfo(info, area, capital, region, subregion, latlng?.toLocation(),
            currencies?.map { it.name }, languages?.map { it.name })
    }

    private fun List<Double>.toLocation() = takeIf { size == 2 }?.let { CountryDetailedInfo.Location(it[0], it[1]) }

    companion object {
        val DETAILED_INFO_FIELDS = arrayOf(
            CountryField.AREA,
            CountryField.CAPITAL,
            CountryField.REGION,
            CountryField.SUBREGION,
            CountryField.LATLNG,
            CountryField.CURRENCIES,
            CountryField.LANGUAGES
        )
    }
}