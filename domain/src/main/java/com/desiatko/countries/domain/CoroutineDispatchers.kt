package com.desiatko.countries.domain

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {
    val ui: CoroutineDispatcher
    val network: CoroutineDispatcher
}