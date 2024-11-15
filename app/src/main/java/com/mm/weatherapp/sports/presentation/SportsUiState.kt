package com.mm.weatherapp.sports.presentation

import com.mm.weatherapp.core.data.network.utils.GeneralError
import com.mm.weatherapp.sports.domain.Sports

data class SportsUiState(
    val isLoading: Boolean = false,
    val sports: Sports? = null,
    val error: GeneralError? = null,
)