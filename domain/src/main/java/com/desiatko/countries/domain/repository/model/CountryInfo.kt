package com.desiatko.countries.domain.repository.model

//interface to delegate these 5 fields
internal interface CountryInfo {
    val alphaCode: String
    val name: String
    val localName: String
    val flag: String
    val population: Int
}