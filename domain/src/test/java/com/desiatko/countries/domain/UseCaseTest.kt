package com.desiatko.countries.domain

import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.repository.RequestHandler
import com.desiatko.countries.domain.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UseCaseTest {

    @Mock
    lateinit var coroutines: CoroutineDispatchers

    @Mock
    lateinit var repository: CountryRepository

    @Mock
    lateinit var requestHandler: RequestHandler<Int>

    lateinit var useCase: UseCase<Int,Int>

    @Before
    fun setUp() {
        useCase = TestUseCase(requestHandler).also {
            it.coroutines = coroutines
            it.repository = repository
        }
        given(coroutines.network).willReturn(Dispatchers.IO)
    }

    @Test
    fun `test requestHandler created and executed`() {
        runBlocking {
            assert(useCase.requestHandler == null)
            useCase.execute(0)
            assert(useCase.requestHandler != null)
            verify(useCase.requestHandler!!).execute()
        }
    }

    @Test
    fun `test cancel`() {
        runBlocking {
            useCase.execute(0)
            useCase.cancel()
            verify(useCase.requestHandler!!).cancel()
        }

    }

    class TestUseCase(val mockRequestHandler: RequestHandler<Int>) : UseCase<Int, Int>() {
        override fun createHandler(params: Int): RequestHandler<Int> = mockRequestHandler
    }
}