package com.mm.weatherapp.sports.data.dataSource

import com.mm.weatherapp.sports.data.dto.SportsDto

interface SportsNetworkDataSource {
    suspend fun getSports(query: String): SportsDto
}