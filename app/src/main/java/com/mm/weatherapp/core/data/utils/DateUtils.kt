package com.mm.weatherapp.core.data.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.toZonedDateTime(format: String = "yyyy-MM-dd HH:mm"): ZonedDateTime {
    val formatter = DateTimeFormatter.ofPattern(format)
    val localDateTime = LocalDateTime.parse(this, formatter)
    val zonedDateTime: ZonedDateTime = localDateTime.atZone((ZoneId.systemDefault()))
    return zonedDateTime
}
