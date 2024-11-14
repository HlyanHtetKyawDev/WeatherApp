package com.mm.weatherapp.search.presentation

import com.mm.weatherapp.core.data.network.utils.GeneralError

sealed class SearchEvent {
    data class Error(val error: GeneralError) : SearchEvent()
}