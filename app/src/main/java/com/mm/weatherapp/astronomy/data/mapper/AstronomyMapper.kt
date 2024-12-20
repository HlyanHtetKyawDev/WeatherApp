package com.mm.weatherapp.astronomy.data.mapper

import com.mm.weatherapp.astronomy.data.dto.AstronomyDto
import com.mm.weatherapp.astronomy.domain.Astronomy
import com.mm.weatherapp.core.data.utils.distanceBetweenSunRiseMoonRise
import com.mm.weatherapp.core.data.utils.distanceBetweenSunSetMoonSet
import com.mm.weatherapp.core.data.utils.distanceWithCurrentLocation
import com.mm.weatherapp.core.data.utils.toZonedDateTime
import com.mm.weatherapp.search.domain.Search
import java.time.ZoneId
import java.time.ZonedDateTime

fun AstronomyDto.toAstronomy(): Astronomy {
    return Astronomy(
        country = location?.country ?: "",
        region = location?.region ?: "",
        name = location?.name ?: "",
        distance = distanceWithCurrentLocation(location?.lat ?: 0.0, location?.lon ?: 0.0),
        localTime = location?.localtime?.toZonedDateTime(
            zoneId = ZoneId.of(
                location.tz_id
            )
        ) ?: ZonedDateTime.now(),
        sunRise = astronomy?.astro?.sunrise ?: "",
        sunSet = astronomy?.astro?.sunset ?: "",
        moonRise = astronomy?.astro?.moonrise ?: "",
        moonSet = astronomy?.astro?.moonset ?: "",
        diffSunRiseMoonRise = distanceBetweenSunRiseMoonRise(
            astronomy?.astro?.sunrise ?: "",
            astronomy?.astro?.moonrise ?: ""
        ),
        diffSunSetMoonSet = distanceBetweenSunSetMoonSet(
            astronomy?.astro?.sunset ?: "",
            astronomy?.astro?.moonset ?: ""
        ),
    )
}


fun Astronomy.toSearch() = Search(
    country = country,
    region = region,
    name = name
)