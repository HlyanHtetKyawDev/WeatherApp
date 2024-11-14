package com.mm.weatherapp.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object ScreenSearch

@Serializable
data class ScreenAstronomy(
    val name: String?
)