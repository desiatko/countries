package com.desiatko.countries.domain.repository.entity

data class Country(
    val alpha2Code: String? = null,
    val alpha3Code: String? = null,
    val altSpellings: List<String>? = null,
    val area: Double? = null,
    val borders: List<String>? = null,
    val callingCodes: List<String>? = null,
    val capital: String? = null,
    val cioc: String? = null,
    val currencies: List<Currency>? = null,
    val demonym: String? = null,
    val flag: String? = null,
    val gini: Double? = null,
    val languages: List<Language>? = null,
    val latlng: List<Double>? = null,
    val name: String? = null,
    val nativeName: String? = null,
    val numericCode: String? = null,
    val population: Int? = null,
    val region: String? = null,
    val regionalBlocs: List<RegionalBloc>? = null,
    val subregion: String? = null,
    val timezones: List<String>? = null,
    val topLevelDomain: List<String>? = null,
    val translations: Translations? = null
)