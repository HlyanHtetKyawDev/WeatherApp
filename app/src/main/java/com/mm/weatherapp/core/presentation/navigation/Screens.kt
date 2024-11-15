package com.mm.weatherapp.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object ScreenLogin

@Serializable
object ScreenSearch

@Serializable
data class ScreenAstronomy(
    val name: String?
)

@Serializable
data class ScreenSports(
    val name: String?
)