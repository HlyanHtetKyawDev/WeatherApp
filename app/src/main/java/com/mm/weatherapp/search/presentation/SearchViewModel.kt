package com.mm.weatherapp.search.presentation

import androidx.lifecycle.viewModelScope
import com.mm.weatherapp.auth.domain.useCase.GoogleSignOutUseCase
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
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val googleSignOutUseCase: GoogleSignOutUseCase,
) : BaseViewModel<SearchEvent>() {

    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.onStart {
        searchCities("Yangon") // as initial city
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SearchUiState())

    private var _loading: Boolean
        get() = state.value.isLoading
        set(value) = _state.update { it.copy(isLoading = value) }

    fun searchCities(query: String) {
        if (query.isNotEmpty()) {
            _state.update {
                it.copy(
                    isLoading = false,
                    searchList = emptyList()
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
                                    error = null,
                                    searchList = result.data.orEmpty()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            googleSignOutUseCase.invoke()
        }
        _state.update {
            it.copy(
                isLogOut = true,
            )
        }
    }
}
