package com.mm.weatherapp.sports.domain.useCase

import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.sports.domain.Sports
import com.mm.weatherapp.sports.domain.repository.SportsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SportsUseCase @Inject constructor(
    private val repository: SportsRepository
) {
    operator fun invoke(query: String): Flow<Resource<Sports>> = repository.getSports(query)
}
