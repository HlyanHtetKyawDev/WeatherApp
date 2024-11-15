package com.mm.weatherapp.sports.data.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GolfCourse
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material.icons.filled.SportsSoccer
import com.mm.weatherapp.core.data.utils.toZonedDateTime
import com.mm.weatherapp.sports.data.dto.SportItemDto
import com.mm.weatherapp.sports.domain.SportItem

fun SportItemDto.toSportItem(sportsType: Int): SportItem {
    return SportItem(
        stadium = stadium,
        country = country,
        tourName = tournament,
        date = start.toZonedDateTime(),
        match = match,
        image = when (sportsType) {
            1 -> Icons.Filled.SportsSoccer
            2-> Icons.Filled.GolfCourse
            3 -> Icons.Filled.SportsCricket
            else -> Icons.Filled.SportsSoccer
        }
    )
}
