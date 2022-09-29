package org.flepper.currencyconvertor.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.flepper.currencyconvertor.data.repositories.GetRatesRepository


class RepositoryHelper() : KoinComponent {
    private val repository:GetRatesRepository by inject()
    fun getRepo() = repository

}

