package com.desiatko.countries.presentation.di.component

import com.desiatko.countries.presentation.di.module.FragmentModule
import com.desiatko.countries.presentation.di.module.PresentersBindingModule
import com.desiatko.countries.presentation.di.module.UseCasesModule
import com.desiatko.countries.presentation.di.scopes.CountryDetailsScope
import com.desiatko.countries.presentation.view.fragment.CountryDetailsFragment
import dagger.Subcomponent

@CountryDetailsScope
@Subcomponent(modules = [FragmentModule::class, UseCasesModule::class, PresentersBindingModule::class])
interface CountryDetailsComponent {
    fun inject(countryDetailsFragment: CountryDetailsFragment)
}

