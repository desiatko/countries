package com.desiatko.countries.presentation

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.repository.RequestHandler
import com.desiatko.countries.domain.repository.Response
import com.desiatko.countries.domain.repository.entity.Country
import com.desiatko.countries.domain.usecase.AllCountriesUseCase
import com.desiatko.countries.domain.usecase.GetCountryDetailsUseCase
import com.desiatko.countries.presentation.coroutines.CoroutineDispatchersImpl
import com.desiatko.countries.presentation.di.component.DaggerApplicationComponent
import com.desiatko.countries.presentation.di.module.ApplicationModule
import com.desiatko.countries.presentation.view.activity.MainActivity
import com.desiatko.countries.presentation.view.adapter.Holder
import com.desiatko.countries.presentation.view.fragment.CountriesListFragment
import com.desiatko.countries.presentation.view.fragment.CountryDetailsFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {

    lateinit var scenario: ActivityScenario<MainActivity>

    @Mock
    lateinit var repository: CountryRepository
    @Mock
    lateinit var countriesCall: RequestHandler<List<Country>>
    @Mock
    lateinit var detailsCall: RequestHandler<Country>

    @Mock
    lateinit var appModule: ApplicationModule

    private val testCountry = Country(
        alpha2Code = "ua",
        name = "Ukraine",
        nativeName = "Urkaine",
        flag = "",
        population = 100500
    )

    @Before
    fun setup() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as CountriesApplication
        app.component = DaggerApplicationComponent.builder()
            .appModule(appModule)
            .appContext(app)
            .build()

        given(appModule.coroutines()).willReturn(CoroutineDispatchersImpl)
        given(appModule.repository()).willReturn(repository)

        given(repository.getAllCountries(*AllCountriesUseCase.SHORT_INFO_FIELDS)).willReturn(countriesCall)
        given(countriesCall.execute()).willReturn(Response.Success(listOf(testCountry)))

        given(repository.getCountryDescription(testCountry.alpha2Code!!, *GetCountryDetailsUseCase.DETAILED_INFO_FIELDS)).willReturn(detailsCall)
        given(detailsCall.execute()).willReturn(Response.Success(testCountry))

        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testListFragmentCreated() {
        //not created yet
        assert(getFragment<CountriesListFragment>() == null)
        scenario.moveToState(Lifecycle.State.CREATED)
        assert(getFragment<CountriesListFragment>() != null)
    }

    @Test
    fun testDetailsFragmentNotCreated() {
        scenario.moveToState(Lifecycle.State.CREATED)
        assert(getFragment<CountryDetailsFragment>() == null)
    }

    @Test
    fun testNavigation() {
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.content)).perform(actionOnItemAtPosition<Holder>(0, click()))
        assert(getFragment<CountryDetailsFragment>() != null)
    }

    @Test
    fun testPresentersPesrsistance() {
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        val listPresenterBefore = getFragment<CountriesListFragment>()!!.presenter
        onView(withId(R.id.content)).perform(actionOnItemAtPosition<Holder>(0, click()))
        val detailsPresenterBefore = getFragment<CountryDetailsFragment>()!!.presenter

        scenario.recreate()

        val listPresenterAfter = getFragment<CountriesListFragment>()!!.presenter
        val detailsPresenterAfter = getFragment<CountryDetailsFragment>()!!.presenter

        assert(listPresenterBefore == listPresenterAfter)
        assert(detailsPresenterBefore == detailsPresenterAfter)
    }

    private inline fun <reified F> getFragment(): F? {
        var fragment: F? = null
        scenario.onActivity { activity ->
            fragment = activity.supportFragmentManager.fragments.find { it::class == F::class } as F?
        }
        return fragment
    }
}