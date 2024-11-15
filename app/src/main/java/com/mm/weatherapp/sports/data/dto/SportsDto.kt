package com.mm.weatherapp.sports.data.dto

import com.mm.weatherapp.core.data.network.dto.Error

data class SportsDto(
    val cricket: List<SportItemDto>,
    val football: List<SportItemDto>,
    val golf: List<SportItemDto>,
    val error: Error?
)