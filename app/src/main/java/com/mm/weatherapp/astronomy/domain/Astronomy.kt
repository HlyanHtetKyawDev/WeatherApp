package com.mm.weatherapp.astronomy.domain

import java.time.ZonedDateTime

data class Astronomy(
    val country: String,
    val region: String,
    val name: String,
    val distance: Long,
    val localTime: ZonedDateTime,
    val sunRise: String,
    val sunSet: String,
    val moonRise: String,
    val moonSet: String,
    val diffSunRiseMoonRise: String,
    val diffSunSetMoonSet: String,
)
