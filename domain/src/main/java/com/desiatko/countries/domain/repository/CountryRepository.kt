package com.desiatko.countries.domain.repository

import com.desiatko.countries.domain.repository.entity.Country
import com.desiatko.countries.domain.repository.entity.CountryField

//repository api, implemented in data module
interface CountryRepository {
    fun getAllCountries(vararg fields: CountryField): RequestHandler<List<Country>>
    fun getCountryDescription(code: String, vararg fields: CountryField): RequestHandler<Country>
}