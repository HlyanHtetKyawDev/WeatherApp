package com.mm.weatherapp.astronomy.data.dataSource

import com.mm.weatherapp.astronomy.data.dto.AstronomyDto

interface AstronomyNetworkDataSource {
    suspend fun getAstronomy(query: String): AstronomyDto
}