package com.desiatko.countries.domain

import com.desiatko.countries.domain.repository.Response
import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.repository.RequestHandler
import com.desiatko.countries.domain.repository.entity.Country
import com.desiatko.countries.domain.repository.model.CountryShortInfo
import com.desiatko.countries.domain.usecase.GetCountryDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCountryDetailsUseCaseTest  {
    @Mock
    lateinit var repository: CountryRepository

    @Mock
    lateinit var coroutines: CoroutineDispatchers

    @Mock
    lateinit var requestHandler: RequestHandler<Country>

    @InjectMocks
    lateinit var useCase: GetCountryDetailsUseCase

    @Mock
    lateinit var countryShortInfo: CountryShortInfo

    private val testCode = "ua"

    @Before
    fun setUp() {
        given(countryShortInfo.alphaCode).willReturn(testCode)
        given(coroutines.network).willReturn(Dispatchers.IO)
        given(repository.getCountryDescription(testCode, *GetCountryDetailsUseCase.DETAILED_INFO_FIELDS)).willReturn(requestHandler)
        given(requestHandler.execute()).willReturn(Response.Error("nevermind"))
    }

    @Test
    fun `test repository api called`() {
        runBlocking {
            useCase.execute(countryShortInfo)
            Mockito.verify(useCase.repository).getCountryDescription(testCode, *GetCountryDetailsUseCase.DETAILED_INFO_FIELDS)
            Mockito.verifyNoMoreInteractions(useCase.repository)
        }
    }
}