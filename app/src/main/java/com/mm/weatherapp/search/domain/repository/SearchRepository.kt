package com.mm.weatherapp.search.domain.repository

import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.search.domain.Search
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String): Flow<Resource<MutableList<Search>>>
}