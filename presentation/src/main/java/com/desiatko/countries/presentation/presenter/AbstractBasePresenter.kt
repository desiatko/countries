package com.desiatko.countries.presentation.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.desiatko.countries.domain.repository.CountryRepository
import com.desiatko.countries.domain.CoroutineDispatchers
import com.desiatko.countries.domain.repository.Response
import com.desiatko.countries.presentation.view.BaseView
import com.desiatko.countries.presentation.view.LoadingContentView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

//main presenters logic
abstract class AbstractBasePresenter<Data, View>(
    val repository: CountryRepository,
    val coroutines: CoroutineDispatchers
) : ViewModel(), BasePresenter<View>, CoroutineScope
        where View : BaseView,
              View : LoadingContentView {


    private val job = Job()
    private val liveData = MutableLiveData<Data>()
    protected lateinit var view: View

    abstract suspend fun executeUseCase(): Response<Data>
    abstract fun updateView(view: View, data: Data)

    override val coroutineContext: CoroutineContext
        get() = coroutines.ui + job

    override fun start(view: View) {
        this.view = view
        val value = liveData.value
        if (value != null) {
            view.showContent()
            updateView(view, value)
        } else {
            liveData.observe(view, Observer { data -> updateView(view, data) })
            launch {
                view.showLoading()
                when (val result = executeUseCase()) {
                    is Response.Success -> {
                        view.showContent()
                        liveData.value = result.data
                    }
                    is Response.Error -> view.showError(result.message)
                }
            }
        }
    }

    override fun onCleared() {
        job.cancel()
    }
}