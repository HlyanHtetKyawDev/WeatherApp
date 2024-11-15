package com.mm.weatherapp.astronomy.domain.useCase

import com.mm.weatherapp.astronomy.domain.Astronomy
import com.mm.weatherapp.astronomy.domain.repository.AstronomyRepository
import com.mm.weatherapp.core.data.network.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AstronomyUseCase @Inject constructor(
    private val repository: AstronomyRepository
) {
    operator fun invoke(query: String): Flow<Resource<Astronomy>> =
        repository.getAstronomy(query)
}
