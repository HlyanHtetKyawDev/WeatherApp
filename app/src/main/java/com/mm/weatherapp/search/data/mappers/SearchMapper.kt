package com.mm.weatherapp.search.data.mappers

import com.mm.weatherapp.search.data.dto.SearchDto
import com.mm.weatherapp.search.domain.Search

fun SearchDto.toSearch(): Search {
    return Search(
        name = name,
        country = country,
        region = region
    )
}