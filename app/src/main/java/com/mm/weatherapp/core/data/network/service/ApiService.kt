package com.mm.weatherapp.core.data.network.service

import com.mm.weatherapp.BuildConfig
import com.mm.weatherapp.core.data.network.EndPoints
import com.mm.weatherapp.search.data.dto.SearchDto
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST(EndPoints.SEARCH)
    fun search(
        @Query("q") query: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY,
    ): Call<List<SearchDto>>

}