package com.desiatko.countries.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {
    private val logger: HttpLoggingInterceptor.Logger
        get() = HttpLoggingInterceptor.Logger {
            Log.d(NetworkRepository.javaClass.simpleName, it)
        }

    private val logging: HttpLoggingInterceptor
        get() = HttpLoggingInterceptor(logger).apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        }

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://restcountries.eu/rest/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}