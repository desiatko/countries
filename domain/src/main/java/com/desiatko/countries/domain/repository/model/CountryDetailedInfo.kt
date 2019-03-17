package com.desiatko.countries.domain.repository.model

import java.io.Serializable

//contains required fields from CountryShortInfo delegated by CountryInfo interface
//and not required fields, which all are nullable
class CountryDetailedInfo(
        info: CountryShortInfo,
        val area: Double?,
        val capital: String?,
        val region: String?,
        val subregion: String?,
        val location: Location?,
        val currencies: List<String>?,
        val languages: List<String>?

) : CountryInfo by info, Serializable {

    class Location(val lat: Double, val long: Double)
}