package com.mm.weatherapp.astronomy.data.mapper

import com.mm.weatherapp.astronomy.data.dto.AstronomyDto
import com.mm.weatherapp.astronomy.domain.Astronomy
import com.mm.weatherapp.core.data.utils.distanceBetweenSunRiseMoonRise
import com.mm.weatherapp.core.data.utils.distanceBetweenSunSetMoonSet
import com.mm.weatherapp.core.data.utils.distanceWithCurrentLocation
import com.mm.weatherapp.search.domain.Search
import java.time.Instant
import java.time.ZoneId

fun AstronomyDto.toAstronomy(): Astronomy {
    return Astronomy(
        country = location.country,
        region = location.region,
        name = location.name,
        distance = distanceWithCurrentLocation(location.lat, location.lon),
        localTime = Instant
            .ofEpochMilli(location.localtime_epoch)
            .atZone(ZoneId.of(location.tz_id)),
        sunRise = astronomy.astro.sunrise,
        sunSet = astronomy.astro.sunset,
        moonRise = astronomy.astro.moonrise,
        moonSet = astronomy.astro.moonset,
        diffSunRiseMoonRise = distanceBetweenSunRiseMoonRise(
            astronomy.astro.sunrise,
            astronomy.astro.moonrise
        ),
        diffSunSetMoonSet = distanceBetweenSunSetMoonSet(
            astronomy.astro.sunset,
            astronomy.astro.moonset
        ),
    )
}


fun Astronomy.toSearch() = Search(
    country = country,
    region = region,
    name = name
)