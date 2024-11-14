package com.mm.weatherapp.search.data.mapper

import com.mm.weatherapp.search.data.dto.SearchDto
import com.mm.weatherapp.search.domain.Search

fun SearchDto.toSearch() = Search(
    name = name,
    country = country,
    region = region
)
