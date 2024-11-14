package com.mm.weatherapp.search.presentation

import com.mm.weatherapp.core.data.network.utils.GeneralError
import com.mm.weatherapp.search.domain.Search

data class SearchUiState(
    val isLoading: Boolean = false,
    val searchList: MutableList<Search>? = null,
    val error: GeneralError? = null,
) {
    val showError: Boolean
        get() = error != null && !isLoading
}
