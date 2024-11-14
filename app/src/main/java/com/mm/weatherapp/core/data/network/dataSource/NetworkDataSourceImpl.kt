package com.mm.weatherapp.core.data.network.dataSource

import com.mm.weatherapp.core.data.network.exceptions.EmptyResponseException
import com.mm.weatherapp.core.data.network.exceptions.FailResponseException
import com.mm.weatherapp.core.data.network.service.ApiService
import com.mm.weatherapp.search.data.dataSource.SearchNetworkDataSource
import com.mm.weatherapp.search.data.dto.SearchDto
import retrofit2.awaitResponse
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : SearchNetworkDataSource {

    override suspend fun search(query: String): List<SearchDto> {
        val response = apiService.search(query).awaitResponse()
        if (!response.isSuccessful) {
            throw FailResponseException(response.message())
        }
        if (response.body() == null) {
            throw EmptyResponseException()
        }
        return response.body()!!
    }
}