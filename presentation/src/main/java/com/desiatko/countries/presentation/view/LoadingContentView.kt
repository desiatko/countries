package com.desiatko.countries.presentation.view

interface LoadingContentView {
    fun showLoading()
    fun showContent()
    fun showError(message: CharSequence)
}