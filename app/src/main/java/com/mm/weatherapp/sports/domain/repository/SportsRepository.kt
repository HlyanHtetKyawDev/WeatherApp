package com.mm.weatherapp.sports.domain.repository

import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.sports.domain.Sports
import kotlinx.coroutines.flow.Flow

interface SportsRepository {
    fun getSports(query: String): Flow<Resource<Sports>>
}