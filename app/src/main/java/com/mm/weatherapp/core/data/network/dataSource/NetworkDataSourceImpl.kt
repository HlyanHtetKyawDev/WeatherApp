package com.mm.weatherapp.core.data.network.dataSource

import com.mm.weatherapp.astronomy.data.dataSource.AstronomyNetworkDataSource
import com.mm.weatherapp.astronomy.data.dto.AstronomyDto
import com.mm.weatherapp.core.data.network.exceptions.EmptyResponseException
import com.mm.weatherapp.core.data.network.exceptions.FailResponseException
import com.mm.weatherapp.core.data.network.service.ApiService
import com.mm.weatherapp.search.data.dataSource.SearchNetworkDataSource
import com.mm.weatherapp.search.data.dto.SearchDto
import com.mm.weatherapp.sports.data.dataSource.SportsNetworkDataSource
import com.mm.weatherapp.sports.data.dto.SportsDto
import retrofit2.awaitResponse
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : SearchNetworkDataSource, AstronomyNetworkDataSource, SportsNetworkDataSource {

    override suspend fun searchCities(query: String): List<SearchDto> {
        val response = apiService.searchCities(query).awaitResponse()
        if (!response.isSuccessful) {
            throw FailResponseException(response.message())
        }
        if (response.body() == null) {
            throw EmptyResponseException()
        }
        return response.body()!!
    }

    override suspend fun getAstronomy(query: String): AstronomyDto {
        val todayDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ZonedDateTime.now())
        val response = apiService.getAstronomy(
            query = query,
            date = todayDate
        ).awaitResponse()
        if (response.body()?.error?.message != null) {
            throw FailResponseException(response.body()?.error?.message ?: response.message())
        }
        if (!response.isSuccessful) {
            throw FailResponseException(response.message())
        }
        if (response.body() == null) {
            throw EmptyResponseException()
        }
        return response.body()!!
    }

    override suspend fun getSports(query: String): SportsDto {
        val response = apiService.getSports(query).awaitResponse()
        if (response.body()?.error?.message != null) {
            throw FailResponseException(response.body()?.error?.message ?: response.message())
        }
        if (!response.isSuccessful) {
            throw FailResponseException(response.message())
        }
        if (response.body() == null) {
            throw EmptyResponseException()
        }
        return response.body()!!
    }
}