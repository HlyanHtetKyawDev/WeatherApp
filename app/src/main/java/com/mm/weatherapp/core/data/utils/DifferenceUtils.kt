package com.mm.weatherapp.core.data.utils

import android.location.Location
import java.text.ParseException
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
    val first = try {
        format.parse(sunRise)
    } catch (e: ParseException) {
        return sunRise
    }
    val second = try {
        format.parse(moonRise)
    } catch (e: Exception) {
        return moonRise
    }
    val millis: Long = (second?.time ?: 0) - (first?.time ?: 0)
    val hours = (millis / (1000 * 60 * 60)).toInt()
    val mins = ((millis / (1000 * 60)) % 60).toInt()
    return "$hours Hour $mins Minutes"
}

fun distanceBetweenSunSetMoonSet(sunSet: String, moonSet: String): String {
    val format = SimpleDateFormat("hh:mm aa", Locale.US)
    val first = try {
        format.parse(sunSet)
    } catch (e: ParseException) {
        return sunSet
    }

    // add 1 day to get next day moonSet time
    val second = try {
        val c: Calendar = Calendar.getInstance()
        c.time = format.parse(moonSet) as Date
        c.add(Calendar.DATE, 1)
        c.time
    } catch (e: ParseException) {
        return moonSet
    }

    val millis: Long = second.time - (first?.time ?: 0)
    val hours = (millis / (1000 * 60 * 60)).toInt()
    val mins = ((millis / (1000 * 60)) % 60).toInt()
    return "$hours Hour $mins Minutes"
}