package com.desiatko.countries.data.service

import com.desiatko.countries.domain.repository.entity.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountriesService {

    @GET("all")
    fun getAllCountries(@Query("filter") filter: Filter): Call<List<Country>>

    @GET("alpha/{code}")
    fun getCountriesByAlphaCode(
            @Path("code") code: String,
            @Query("filter") filter: Filter
    ): Call<Country>
}