package com.mm.weatherapp.core.data.network.service

import com.mm.weatherapp.astronomy.data.dto.AstronomyDto
import com.mm.weatherapp.core.data.network.EndPoints
import com.mm.weatherapp.search.data.dto.SearchDto
import com.mm.weatherapp.sports.data.dto.SportsDto
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST(EndPoints.SEARCH)
    fun searchCities(
        @Query("q") query: String,
    ): Call<List<SearchDto>>

    @POST(EndPoints.ASTRONOMY)
    fun getAstronomy(
        @Query("q") query: String,
        @Query("dt") date: String,
    ): Call<AstronomyDto>

    @POST(EndPoints.SPORTS)
    fun getSports(
        @Query("q") query: String,
    ): Call<SportsDto>
}