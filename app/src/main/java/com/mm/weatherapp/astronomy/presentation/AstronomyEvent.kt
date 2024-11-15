package com.mm.weatherapp.astronomy.presentation

import com.mm.weatherapp.core.data.network.utils.GeneralError

sealed class AstronomyEvent {
    data class Error(val error: GeneralError) : AstronomyEvent()
}