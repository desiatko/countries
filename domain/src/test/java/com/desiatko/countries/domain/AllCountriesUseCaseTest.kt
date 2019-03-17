package com.desiatko.countries.domain

import com.desiatko.countries.domain.repository.Response
import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.repository.RequestHandler
import com.desiatko.countries.domain.repository.entity.Country
import com.desiatko.countries.domain.usecase.AllCountriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AllCountriesUseCaseTest {

    @Mock
    lateinit var repository: CountryRepository

    @Mock
    lateinit var coroutines: CoroutineDispatchers

    @Mock
    lateinit var requestHandler: RequestHandler<List<Country>>

    @InjectMocks
    lateinit var useCase: AllCountriesUseCase

    @Before
    fun setUp() {
        given(coroutines.network).willReturn(Dispatchers.IO)
        given(repository.getAllCountries(*AllCountriesUseCase.SHORT_INFO_FIELDS)).willReturn(requestHandler)
        given(requestHandler.execute()).willReturn(Response.Success(emptyList()))
    }

    @Test
    fun `test repository api called`() {
        runBlocking {
            useCase.execute(Unit)
            verify(useCase.repository).getAllCountries(*AllCountriesUseCase.SHORT_INFO_FIELDS)
            verifyNoMoreInteractions(useCase.repository)
        }
    }
}