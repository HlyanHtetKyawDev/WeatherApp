package com.mm.weatherapp.search.data.dataSource

import com.mm.weatherapp.search.data.dto.SearchDto

interface SearchNetworkDataSource {
    suspend fun search(query: String): List<SearchDto>
}