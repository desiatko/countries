package com.desiatko.countries.presentation.di.component

import com.desiatko.countries.presentation.di.module.FragmentModule
import com.desiatko.countries.presentation.di.module.PresentersBindingModule
import com.desiatko.countries.presentation.di.module.UseCasesModule
import com.desiatko.countries.presentation.di.scopes.CountriesListScope
import com.desiatko.countries.presentation.view.fragment.CountriesListFragment
import dagger.Subcomponent

@CountriesListScope
@Subcomponent(modules = [FragmentModule::class, UseCasesModule::class, PresentersBindingModule::class])
interface CountriesListComponent {
    fun inject(countryListFragment: CountriesListFragment)
}