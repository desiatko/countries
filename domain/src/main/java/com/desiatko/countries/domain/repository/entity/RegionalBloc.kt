package com.desiatko.countries.domain.repository.entity

data class RegionalBloc(
        val acronym: String,
        val name: String,
        val otherAcronyms: List<String>,
        val otherNames: List<String>
)