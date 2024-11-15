package com.mm.weatherapp.sports.presentation

import com.mm.weatherapp.core.data.network.utils.GeneralError

sealed class SportsEvent {
    data class Error(val error: GeneralError) : SportsEvent()
}