package com.desiatko.countries.presentation.presenter

interface BasePresenter<View> {
    fun start(view: View)
}