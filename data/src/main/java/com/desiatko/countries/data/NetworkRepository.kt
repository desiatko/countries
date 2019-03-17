package com.desiatko.countries.data
import com.desiatko.countries.data.service.CountriesService
import com.desiatko.countries.data.service.Filter
import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.repository.RequestHandler
import com.desiatko.countries.domain.repository.Response
import com.desiatko.countries.domain.repository.entity.Country
import com.desiatko.countries.domain.repository.entity.CountryField
import retrofit2.Call

const val UNKNOWN_ERROR = "Unknown error"

object NetworkRepository : CountryRepository {

    private val retrofit = RetrofitFactory.retrofit

    private val countriesService by lazy { retrofit.create(CountriesService::class.java) }

    class ErrorResponse(val message: String)

    //error message deserializer from okhttp3.ResponseBody
    private val errorConverter
        get() = retrofit.responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, emptyArray())

    override fun getAllCountries(vararg fields: CountryField): RequestHandler<List<Country>> =
        countriesService.getAllCountries(Filter(fields)).requestHandler

    override fun getCountryDescription(code: String, vararg fields: CountryField): RequestHandler<Country> =
        countriesService.getCountriesByAlphaCode(code, Filter(fields)).requestHandler

    //helper method to map retrofit.Call
    val <T> Call<T>.requestHandler: RequestHandler<T>
        get() = object : RequestHandler<T> {
            override fun execute(): Response<T> {
                return try {
                    this@requestHandler.execute().wrap()
                } catch (e: Exception) {
                    Response.Error(e.message ?: UNKNOWN_ERROR)
                }
            }

            override fun cancel() {
                this@requestHandler.cancel()
            }
        }

    //wraps retrofit2.Response with interface from domain layer to cover network implementation
    fun <T> retrofit2.Response<T>.wrap(): Response<T> {
        return if (isSuccessful) {
            Response.Success(body()!!)
        } else {
            Response.Error(errorBody()?.let { errorConverter.convert(it) }?.message ?: UNKNOWN_ERROR)
        }
    }
}
