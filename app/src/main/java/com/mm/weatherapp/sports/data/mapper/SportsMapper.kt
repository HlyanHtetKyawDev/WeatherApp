package com.mm.weatherapp.sports.data.mapper

import com.mm.weatherapp.core.data.utils.toZonedDateTime
import com.mm.weatherapp.sports.data.dto.SportItemDto
import com.mm.weatherapp.sports.domain.SportItem

fun SportItemDto.toSportItem(): SportItem {
    return SportItem(
        stadium = stadium,
        country = country,
        tourName = tournament,
        date = start.toZonedDateTime(),
        match = match
    )
}
