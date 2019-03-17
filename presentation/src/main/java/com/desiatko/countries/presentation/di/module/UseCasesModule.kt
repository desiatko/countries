package com.desiatko.countries.presentation.di.module

import com.desiatko.countries.domain.CoroutineDispatchers
import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.usecase.AllCountriesUseCase
import com.desiatko.countries.domain.usecase.GetCountryDetailsUseCase
import com.desiatko.countries.domain.usecase.UseCase
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun allCountriesUseCase(repo: CountryRepository, coroutines: CoroutineDispatchers): AllCountriesUseCase =
        buildUseCase(repo, coroutines) { AllCountriesUseCase() }

    @Provides
    fun countryDetailsUseCase(repo: CountryRepository, coroutines: CoroutineDispatchers): GetCountryDetailsUseCase =
        buildUseCase(repo, coroutines) { GetCountryDetailsUseCase() }

    private fun <T : UseCase<*, *>> buildUseCase(
        repository: CountryRepository,
        coroutines: CoroutineDispatchers,
        creator: () -> T
    ): T = creator().also {
        it.repository = repository
        it.coroutines = coroutines
    }
}