package com.desiatko.countries.domain.repository

//class to encapsulate response from data storage
sealed class Response<out T> {
    class Success<out T>(val data: T) : Response<T>()
    class Error(val message: CharSequence) : Response<Nothing>()
}

//class for handling request to data storage
interface RequestHandler<out T> {
    fun execute(): Response<T>
    fun cancel()
}

//mapping function Response<T> -> Response<R>
fun <T, R> Response<T>.map(mapper: (T) -> R): Response<R> {
    return when (this) {
        is Response.Success<T> -> Response.Success(mapper(data))
        is Response.Error -> this
    }
}

//mapping function RequestHandler<T> -> RequestHandler<R>
fun <T, R> RequestHandler<T>.map(mapper: (T) -> R): RequestHandler<R> =
    object : RequestHandler<R> {
        override fun execute(): Response<R> = this@map.execute().map(mapper)
        override fun cancel() = this@map.cancel()
    }
