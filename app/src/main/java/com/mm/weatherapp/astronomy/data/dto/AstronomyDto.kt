package com.mm.weatherapp.astronomy.data.dto

import com.mm.weatherapp.core.data.network.dto.Error

data class AstronomyDto(
    val astronomy: AstronomyVO,
    val location: Location,
    val error: Error?
)