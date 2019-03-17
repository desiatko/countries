package com.desiatko.countries.presentation.coroutines

import com.desiatko.countries.domain.CoroutineDispatchers
import kotlinx.coroutines.*

object CoroutineDispatchersImpl : CoroutineDispatchers {
    override val ui = Dispatchers.Main
    override val network = Dispatchers.IO
}