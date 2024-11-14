package com.mm.weatherapp.search.presentation

import androidx.lifecycle.viewModelScope
import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.core.presentation.BaseViewModel
import com.mm.weatherapp.search.domain.useCase.SearchCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase
) : BaseViewModel<SearchEvent>() {

    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.onStart {
        searchCities("Yangon") // as initial city
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SearchUiState())

    private var _loading: Boolean
        get() = state.value.isLoading
        set(value) = _state.update { it.copy(isLoading = value) }

    fun searchCities(query: String) {
        _state.update {
            it.copy(
                isLoading = false
            )
        }
        viewModelScope.launch {
            searchCitiesUseCase(query).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.error
                            )
                        }
                        emitEvent(SearchEvent.Error(result.error))
                    }

                    is Resource.Loading -> _loading = true
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                searchList = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun clearError() {
        _state.update {
            it.copy(
                error = null
            )
        }
    }
}
