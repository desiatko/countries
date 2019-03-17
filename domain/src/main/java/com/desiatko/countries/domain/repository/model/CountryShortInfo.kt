package com.desiatko.countries.domain.repository.model

import java.io.Serializable

//contains required country information to show in list
open class CountryShortInfo(
        override val alphaCode: String,
        override val name: String,
        override val localName: String,
        override val flag: String,
        override val population: Int
) : CountryInfo, Serializable