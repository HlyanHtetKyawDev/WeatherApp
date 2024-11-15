package com.mm.weatherapp.astronomy.presentation

import com.mm.weatherapp.astronomy.domain.Astronomy
import com.mm.weatherapp.core.data.network.utils.GeneralError

data class AstronomyUiState(
    val isLoading: Boolean = false,
    val astronomy: Astronomy? = null,
    val error: GeneralError? = null,
)