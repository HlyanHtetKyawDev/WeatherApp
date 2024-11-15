package com.mm.weatherapp.astronomy.domain.repository

import com.mm.weatherapp.astronomy.domain.Astronomy
import com.mm.weatherapp.core.data.network.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AstronomyRepository {
    fun getAstronomy(query: String): Flow<Resource<Astronomy>>
}