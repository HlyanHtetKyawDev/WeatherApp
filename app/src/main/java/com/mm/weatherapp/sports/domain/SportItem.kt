package com.mm.weatherapp.sports.domain

import java.time.ZonedDateTime

data class SportItem(
    val stadium: String,
    val country: String,
    val tourName: String,
    val date: ZonedDateTime,
    val match: String,
)
