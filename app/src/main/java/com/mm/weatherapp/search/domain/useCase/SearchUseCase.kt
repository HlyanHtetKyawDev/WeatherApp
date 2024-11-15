package com.mm.weatherapp.search.domain.useCase

import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.search.domain.Search
import com.mm.weatherapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Search>>> =
        repository.searchCities(query)
}
