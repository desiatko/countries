package com.desiatko.countries.domain.repository.entity


enum class CountryField(override val fullName: String) : Field {
    ALPHA2CODE("alpha2Code"),
    ALPHA3CODE("alpha3Code"),
    ALT_SPELLINGS("altSpellings"),
    AREA("area"),
    BORDERS("borders"),
    CALLING_CODES("callingCodes"),
    CAPITAL("capital"),
    CIOC("cioc"),
    CURRENCIES("currencies"),
    DEMONYM("demonym"),
    FLAG("flag"),
    GINI("gini"),
    LANGUAGES("languages"),
    LATLNG("latlng"),
    NAME("name"),
    NATIVE_NAME("nativeName"),
    NUMERIC_CODE("numericCode"),
    POPULATION("population"),
    REGION("region"),
    REGIONAL_BLOCS("regionalBlocs"),
    SUBREGION("subregion"),
    TIMEZONES("timezones"),
    TOP_LEVEL_DOMAIN("topLevelDomain"),
    TRANSLATIONS("translations")
}