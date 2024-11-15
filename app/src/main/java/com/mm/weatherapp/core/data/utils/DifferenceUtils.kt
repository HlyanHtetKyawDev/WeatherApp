package com.mm.weatherapp.core.data.utils

import android.location.Location
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun distanceWithCurrentLocation(lat: Double, lng: Double): Long {
    val currentLocation = Location("currentLocation")
    currentLocation.latitude = 13.5853959
    currentLocation.longitude = 100.6112514

    val searchLocation = Location("searchLocation")
    searchLocation.latitude = lat
    searchLocation.longitude = lng

    return currentLocation.distanceTo(searchLocation).toLong()
}

fun distanceBetweenSunRiseMoonRise(sunRise: String, moonRise: String): String {
    val format = SimpleDateFormat("hh:mm aa", Locale.US)
    val first = format.parse(sunRise)
    val second = format.parse(moonRise)
    val millis: Long = (second?.time ?: 0) - (first?.time ?: 0)
    val hours = (millis / (1000 * 60 * 60)).toInt()
    val mins = ((millis / (1000 * 60)) % 60).toInt()
    return "$hours Hour $mins Minutes"
}

fun distanceBetweenSunSetMoonSet(sunSet: String, moonSet: String): String {
    val format = SimpleDateFormat("hh:mm aa", Locale.US)
    val first = format.parse(sunSet)

    // add 1 day to get next day moonSet time
    val c: Calendar = Calendar.getInstance()
    c.time = format.parse(moonSet) as Date
    c.add(Calendar.DATE, 1)
    val second = c.time

    val millis: Long = second.time - (first?.time ?: 0)
    val hours = (millis / (1000 * 60 * 60)).toInt()
    val mins = ((millis / (1000 * 60)) % 60).toInt()
    return "$hours Hour $mins Minutes"
}