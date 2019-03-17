package com.desiatko.countries.domain.usecase

import com.desiatko.countries.domain.CoroutineDispatchers
import com.desiatko.countries.domain.repository.Response
import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.repository.RequestHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

abstract class UseCase<Params, Result> : CoroutineScope {

    lateinit var repository: CountryRepository
    lateinit var coroutines: CoroutineDispatchers

    private val job = Job()

    var requestHandler: RequestHandler<*>? = null

    abstract fun createHandler(params: Params): RequestHandler<Result>

    override val coroutineContext: CoroutineContext
        get() = coroutines.network + job

    suspend fun execute(params: Params): Response<Result> {
        val requestHandler = createHandler(params)
        this@UseCase.requestHandler = requestHandler
        return async { requestHandler.execute() }.await()
    }

    fun cancel() {
        requestHandler?.cancel()
        job.cancel()
    }
}