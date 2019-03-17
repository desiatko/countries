package com.desiatko.countries.presentation.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.desiatko.countries.presentation.CountriesApplication
import com.desiatko.countries.presentation.R
import com.desiatko.countries.presentation.di.component.ApplicationComponent

fun <T : View> View.id(@IdRes id: Int) = lazy { findViewById<T>(id) }

fun <T : View> RecyclerView.ViewHolder.id(@IdRes id: Int) = itemView.id<T>(id)

@Suppress("UNCHECKED_CAST")
fun <T> unchckedCast(obj: Any): T = obj as T

inline fun <reified T : ViewModel> createPresenter(fragment: Fragment, factory: ViewModelProvider.Factory): T =
    ViewModelProviders.of(fragment, factory)[T::class.java]

val Activity.component: ApplicationComponent
    get() = (application as CountriesApplication).component

val Fragment.component: ApplicationComponent
    get() = activity?.component ?: throw IllegalStateException("Fragment is not attached to activity")

val Resources.isTablet: Boolean get() = getBoolean(R.bool.isTablet)
val Context.isTablet: Boolean get() = resources.isTablet