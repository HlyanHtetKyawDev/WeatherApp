package com.mm.weatherapp.core.data.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.toZonedDateTime(
    format: String = "yyyy-MM-dd HH:mm",
    zoneId: ZoneId = ZoneId.systemDefault()
): ZonedDateTime {
    val formatter = DateTimeFormatter.ofPattern(format)
    val localDateTime = LocalDateTime.parse(this, formatter)
    val zonedDateTime: ZonedDateTime = localDateTime.atZone((zoneId))
    return zonedDateTime
}


fun ZonedDateTime.toStringTime(format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    return this.format(formatter)
}